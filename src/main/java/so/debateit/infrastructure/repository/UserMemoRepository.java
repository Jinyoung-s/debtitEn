/**
 * The UserMemoRepository class for JPA repository
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/11/2022
 */
package so.debateit.infrastructure.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import so.debateit.common.model.User;
import so.debateit.domain.model.memo.UserMemo;

import java.util.List;

public interface UserMemoRepository extends JpaRepository<UserMemo, Long> {
    List<UserMemo> findByRecvUser(User user, Pageable pageable);

    List<UserMemo> findByRecvUserAndReadCunt(User user, Long readCunt, Pageable pageable);
}
