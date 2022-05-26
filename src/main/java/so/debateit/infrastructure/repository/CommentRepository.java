/**
 * The CommentRepository class for JPA repository
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/12/2022
 */

package so.debateit.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import so.debateit.domain.model.chat.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment>  findByBoadNmbr(Long boadNmbr);
}
