package so.debateit.domain.application;

import so.debateit.domain.model.board.Board;
import so.debateit.domain.model.chat.Comment;
import so.debateit.domain.model.user.SessionUser;
import so.debateit.web.model.BoardLikePayLoad;
import so.debateit.web.model.BoardListParam;
import so.debateit.web.model.BoardResult;

import java.util.List;

public interface BoardService {
    void writeBoard(Board board);

    List<Board> getPopularBoard();

    BoardResult getBoard(Long boardId, SessionUser user);

    BoardResult joinDebate(Board board, SessionUser user);

    BoardResult addLike(BoardLikePayLoad boardLike, SessionUser user);

    void saveComment(Comment comment, SessionUser user);

    void deleteComment(Comment comment, SessionUser user);

    BoardResult getFreeBoardList(BoardListParam boardListParam);

    void deleteBoard(Board board);
}
