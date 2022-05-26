/**
 * The BoardRepository class for JPA repository
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/1/2022
 */
package so.debateit.infrastructure.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import so.debateit.domain.model.board.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByBoadDvsn(String boadDvsn, Pageable pageable);

    @Query(value="(select * from boad_info board where boad_dvsn = 'debate' AND cret_date > date_add(now(), interval -:hour hour) order by totl_cunt desc limit 10)\n" +
            "UNION \n" +
            "(select * from boad_info board where boad_dvsn = 'free' AND cret_date > date_add(now(), interval -:hour hour) order by totl_cunt desc limit 10);", nativeQuery = true)
    List<Board> findTop10Board(@Param("hour")Long hour);

    Long countByBoadDvsn(String boadDvsn);
}
