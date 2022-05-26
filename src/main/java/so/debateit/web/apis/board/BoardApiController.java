/**
 * The BoardApiController is the controller for posting
 * and listing the post
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/02/2022
 */
package so.debateit.web.apis.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import so.debateit.domain.application.BoardService;
import so.debateit.domain.model.board.Board;
import so.debateit.domain.model.chat.Comment;
import so.debateit.domain.model.user.SessionUser;
import so.debateit.web.model.BoardLikePayLoad;
import so.debateit.web.model.BoardListParam;
import so.debateit.web.model.BoardResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/api")
public class BoardApiController {

    @Autowired
    private BoardService service;

    @Autowired
    private BoardResult boardResult;

    @PostMapping("/writeBoard")
    @ResponseBody
    public Board writeBoard(@RequestBody Board board) {
        service.writeBoard(board);
        return board;
    }

    @PostMapping("/getPopularBoard")
    @ResponseBody
    public List<Board> getPopularBoard(@RequestBody Board board, HttpServletRequest request) {
        return service.getPopularBoard();
    }

    @PostMapping("/getBoard")
    @ResponseBody
    public BoardResult getBoard(@RequestBody Board board, @AuthenticationPrincipal SessionUser user) {
        return service.getBoard(board.getBoadNmbr(), user);
    }

    @PostMapping("/joinDebate")
    @ResponseBody
    public BoardResult joinDebate(@RequestBody Board board, @AuthenticationPrincipal SessionUser user) {
        return service.joinDebate(board, user);
    }

    @PostMapping("/addLike")
    @ResponseBody
    public BoardResult addLike(@RequestBody BoardLikePayLoad boardLike, @AuthenticationPrincipal SessionUser user) {
        return service.addLike(boardLike, user);

    }

    @PostMapping("/saveComment")
    @ResponseBody
    public void saveComment(@RequestBody Comment comment, @AuthenticationPrincipal SessionUser user) {
        service.saveComment(comment, user);
    }

    @PostMapping("/deleteComment")
    @ResponseBody
    public void deleteComment(@RequestBody Comment comment, @AuthenticationPrincipal SessionUser user) {
        service.deleteComment(comment, user);
    }

    @PostMapping("/getFreeBoardList")
    @ResponseBody
    public BoardResult getFreeBoardList(@RequestBody BoardListParam param) {
        return  service.getFreeBoardList(param);
    }

    @PostMapping("/deleteBoard")
    @ResponseBody
    public void deleteBoard(@RequestBody Board board) {
        service.deleteBoard(board);
    }
}
