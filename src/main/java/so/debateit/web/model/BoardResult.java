package so.debateit.web.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import so.debateit.domain.model.board.Board;
import so.debateit.domain.model.chat.ChatInfo;
import so.debateit.domain.model.chat.CommentTree;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
public class BoardResult extends BaseResult{
    private Board board;
    private List<Board> boardList = new ArrayList<Board>();
    private boolean chatUser = false;
    private String chatData;
    private List<ChatInfo> chatList = new ArrayList<ChatInfo>();
    private List<CommentTree> commentList = new ArrayList<CommentTree>();
    private Double boardCount;
}
