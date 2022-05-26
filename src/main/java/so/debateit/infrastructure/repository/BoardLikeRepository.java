/**
 * The BoardLikeRepository class for JPA repository
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/23/2022
 */

package so.debateit.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import so.debateit.domain.model.board.BoardLike;

import java.util.List;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    List<BoardLike> findByUserNmbrAndBoadNmbr(Long userNmbr, Long boadNmbr);

}
