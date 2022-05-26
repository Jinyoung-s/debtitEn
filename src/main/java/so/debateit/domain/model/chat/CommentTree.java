/**
 * The CommentTree class is for the tree structure of post comment list
 * The method searching the comment List has a recursive form
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/12/2022
 */

package so.debateit.domain.model.chat;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommentTree extends Comment{
    private List<CommentTree> childList = new ArrayList<CommentTree>();

    private boolean openReply;

    private boolean openModify = false;
}
