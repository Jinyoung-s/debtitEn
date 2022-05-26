/**
 * The BoardServiceImpl class implements the function of reading and writing
 * and listing the post
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/01/2022
 */
package so.debateit.domain.application.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;
import so.debateit.common.model.BoardSpecs;
import so.debateit.common.model.User;
import so.debateit.config.UserNotiException;
import so.debateit.domain.application.BoardService;
import so.debateit.domain.model.board.Board;
import so.debateit.domain.model.board.BoardLike;
import so.debateit.domain.model.chat.ChatInfo;
import so.debateit.domain.model.chat.Comment;
import so.debateit.domain.model.chat.CommentTree;
import so.debateit.domain.model.user.SessionUser;
import so.debateit.infrastructure.repository.*;
import so.debateit.util.DebateEnvironment;
import so.debateit.util.FileUtil;
import so.debateit.web.model.BoardLikePayLoad;
import so.debateit.web.model.BoardListParam;
import so.debateit.web.model.BoardResult;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardResult boardResult;
    //private final ElasticChatRepository elasticChatRepository;
    private final ChatInfoRepository chatInfoRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardLike boardLike;
    private final CommentRepository commentRepository;
    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final UserPoinRepository userPoinRepository;
    private final FileUtil fileUtil;

    @Value("${custom.uploadPath}")
    private String uploadPath;

    public void writeBoard(Board board ) {
        SessionUser session = (SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean isModify = board.getBoadNmbr() != null;
        User userInfo =  userService.getUserInfo(session);
        board.setFirstUser(userInfo);
        board.getFirstUser().setUserNmbr(session.getUserNmbr());
        board.setCretDate(DateUtils.createNow().getTime());


        String contents = board.getBoadCont();
        Pattern pattern = Pattern.compile("<img.*?\">");
        Matcher matcher = pattern.matcher(contents);

        int idx = 0;
        String repStr = "@@replaceDebateCheck@@";
        List<String> replaceList = new ArrayList<String>();
        while(matcher.find()) {
            String imgString = matcher.group();
            String imgStringConv = imgString.replaceAll("<img src=\"", "").replaceAll("\">", "");
            String imgPath = fileUtil.fileMove(imgStringConv);
            replaceList.add("<img src=\""+ DebateEnvironment.uploadImgServer +imgPath + "\">");
            contents = contents.replaceFirst("<img.*?\">", repStr + idx);
            if(idx == 0) { //first image
                String sumPath = fileUtil.makeThumbNail(DebateEnvironment.uploadPath + imgPath, imgStringConv);
                if(sumPath != null) {
                    board.setSumnPath(sumPath);
                }
            }
            idx++;
        }

        for(int i = 0; i < replaceList.size(); i++) {
            contents = contents.replace(repStr+i, replaceList.get(i));
        }

        if(BoardSpecs.BoardDvsn.getDebate().stream().anyMatch(v -> v.getValue().equals(board.getBoadDvsn()))) {
            board.setBoadKind(BoardSpecs.BoardKind.D.getValue());
        } else {
            board.setBoadKind(BoardSpecs.BoardKind.N.getValue());
        }

        board.setBoadCont(contents);

        if(isModify) {
            Board preboard = boardRepository.getById(board.getBoadNmbr());
            preboard.setBoadCont(board.getBoadCont());
            preboard.setBoadTitl(board.getBoadTitl());
            boardRepository.save(preboard);
        } else {
            boardRepository.save(board);
            userService.userPointUpdate(userInfo, 5L);
        }
    }

    public List<Board> getPopularBoard() {
        List<Board> boardList = boardRepository.findTop10Board(12L);
        boardList = boardRepository.findTop10Board(72L);
        return boardList;
    }

    // get board info
    public BoardResult getBoard(Long boardId, SessionUser user) {
        SearchSourceBuilder se = new SearchSourceBuilder();


        List<ChatInfo> chatList =  chatInfoRepository.findByBoadNmbr(boardId.toString());
        boardResult.setChatList(chatList);

        /* TODO need to think about server memory because elasticsearch need a lot of memory
            Iterable<Chat> chatIterable = elasticChatRepository.findByRoomNoOrderByCretDate(boardId.toString());
            chatIterable.forEach(chatList::add);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        */

        Board board = boardRepository.getById(boardId);
        if(user != null) {
            if(board.getFirstUser().getUserNmbr().equals(user.getUserNmbr()) || (board.getSecondUser() != null && board.getSecondUser().getUserNmbr().equals(user.getUserNmbr()))) {
                boardResult.setChatUser(true);
            }
        }

        board.setReadCunt(board.getReadCunt() != null ? board.getReadCunt() + 1 : 1);
        boardRepository.save(board);

        List<Comment> commentList = commentRepository.findByBoadNmbr(boardId);
        List<CommentTree> commentTreeList = new ArrayList<CommentTree>();

        for(Comment comment : commentList) {
            CommentTree commentTree = new CommentTree();
            commentTree.setCmntNmbr(comment.getCmntNmbr());
            commentTree.setUserNick(comment.getUserNick());
            commentTree.setCmntMesg(comment.getCmntMesg());
            commentTree.setCretUser(comment.getCretUser());
            commentTree.setCretDate(comment.getCretDate());
            commentTree.setUser(comment.getUser());
            commentTree.setParent(comment.getParent());
            commentTree.setDeleDvsn(comment.getDeleDvsn());
            if(StringUtils.equals(commentTree.getDeleDvsn(), "Y")) {
                commentTree.setUserNick("");
                commentTree.setCmntMesg("");
            }

            commentTreeList.add(commentTree);
        }

        boardResult.setCommentList(setTreeComment(commentTreeList));
        boardResult.setBoard(board);
        return boardResult;
    }

    @Override
    public BoardResult joinDebate(Board board, SessionUser user) {

        Board currentBoard = boardRepository.getById(board.getBoadNmbr());

        //If there is another user in board, returns an error
        if(currentBoard.getSecondUser() != null && currentBoard.getSecondUser().getUserNmbr() != null) {
            boardResult.setSuccessStatus(false);
            boardResult.setErrMsg("error.debate.alreadyExist");
            return boardResult;
        }

        currentBoard.setSecondUser(userService.getUserInfo(user));
        currentBoard.setUpdtDate(DateUtils.createNow().getTime());
        currentBoard.setUpdtUser(user.getUserNmbr());
        boardRepository.save(currentBoard);

        boardResult.setBoard(currentBoard);
        return boardResult;
    }

    @Override
    public BoardResult addLike(BoardLikePayLoad boardLikePayLoad, SessionUser user) {
        boardLike.setLikeType(boardLikePayLoad.getType());
        boardLike.setBoadNmbr(boardLikePayLoad.getBoadNmbr());
        boardLike.setUserNmbr(user.getUserNmbr());
        boardLike.setCretDate(DateUtils.createNow().getTime());
        boardLike.setCretUser(user.getUserNmbr());

        Board board = boardRepository.getById(boardLikePayLoad.getBoadNmbr());


        //1. check close date
        if(board.getClosTime() != null) {
            // throw new UserNotiException("alert.alreadyClose");
        }

        //2. user who's already clicked cannot click
        List<BoardLike> existBoardLikeList = boardLikeRepository.findByUserNmbrAndBoadNmbr(boardLike.getUserNmbr(), boardLike.getBoadNmbr());
        if(existBoardLikeList.size() > 0) {
            //
            throw new UserNotiException("alert.alreadyLike");
        }

        //3. update board 'Likes'
        if(boardLikePayLoad.getType().equals(BoardSpecs.BoardUserType.OWNER.getValue())) {
            board.setAgreCunt(board.getAgreCunt() == null ? 1L : board.getAgreCunt() + 1L);
            if(board.getOppoCunt() == null) {
                board.setOppoCunt(0L);
            }
        } else {
            board.setOppoCunt(board.getOppoCunt() == null ? 1L : board.getOppoCunt() + 1L);
            if(board.getAgreCunt() == null) {
                board.setAgreCunt(0L);
            }
        }

        board.setTotlCunt(board.getAgreCunt() + board.getOppoCunt());

        //4. set winner
        if(board.getSecondUser() != null && board.getSecondUser().getUserNmbr() > 0 && board.getWinUser() == null ){
            if(board.getAgreCunt() != null && (board.getWinaLike().equals(board.getAgreCunt()) || board.getWinaLike().compareTo(board.getAgreCunt()) < 0 )) {
                board.setWinUser(board.getFirstUser());
            }

            if(board.getOppoCunt() != null && (board.getWinaLike().equals(board.getOppoCunt())  || board.getWinaLike().compareTo(board.getOppoCunt()) < 0)) {
                board.setWinUser(board.getSecondUser());
            }
        }

        boardLikeRepository.save(boardLike);
        boardRepository.save(board);

        User userInfo =  userService.getUserInfo(user);
        userService.userPointUpdate(userInfo, 1L);

        boardResult.setBoard(board);
        return boardResult;
    }


    @Override
    public void saveComment(Comment comment, SessionUser sessionUser) {
        comment.getUser().setUserNmbr(sessionUser.getUserNmbr());
        comment.setUserNick(sessionUser.getUserNick());
        comment.setCretUser(sessionUser.getUserNmbr());

        comment.setCretDate(DateUtils.createNow().getTime());
        commentRepository.saveAndFlush(comment);

        User userInfo =  userService.getUserInfo(sessionUser);
        userService.userPointUpdate(userInfo, 2L);

    }

    @Override
    public void deleteComment(Comment comment, SessionUser sessionUser) {
        if (comment.getUser().getUserNmbr().equals(sessionUser.getUserNmbr())) {
            comment.setDeleDvsn("Y");
            commentRepository.save(comment);
        }
    }

    private List<CommentTree> setTreeComment(List<CommentTree> commentList) {

        List<CommentTree> CommentListTree = new ArrayList<CommentTree>();

        for(int i = 0; i < commentList.size() ; i++) {
            CommentTree com = commentList.get(i);
            if(com.getParent() == null ) {
                CommentListTree.add(setChild(commentList, com));
            }
        }
        return CommentListTree;
    }

    private CommentTree setChild(List<CommentTree> commentList, CommentTree parentComment) {
        for(int i = 0; i < commentList.size(); i++) {
            CommentTree com = commentList.get(i);
            if(com.getParent() == null ){
                continue;
            }


            if(parentComment.getCmntNmbr() == com.getParent().getCmntNmbr() ) {
                parentComment.getChildList().add(com);
            }
        }

        if(parentComment.getChildList().size() != 0){
            for(int i = 0; i < parentComment.getChildList().size(); i++) {
                setChild(commentList, parentComment.getChildList().get(i));
            }
        }

        return parentComment;
    }

    public BoardResult getFreeBoardList(BoardListParam boardListParam) {
        Pageable sortedByName = PageRequest.of(boardListParam.getPageNo(), 20, Sort.by("cretDate").descending());
        Long count = boardRepository.countByBoadDvsn(boardListParam.getBoadDvsn());
        BoardResult boardResult = new BoardResult();
        List<Board> boardList = boardRepository.findAllByBoadDvsn(boardListParam.getBoadDvsn(), sortedByName);
        boardResult.setBoardList(boardList);

        double countCut= Math.ceil((double)count/20L);

        boardResult.setBoardCount(countCut);
        return boardResult;
    }

    @Override
    public void deleteBoard(Board board) {
        boardRepository.delete(board);
    }
}
