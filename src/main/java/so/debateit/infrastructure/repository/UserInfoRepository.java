/**
 * The UserInfoRepository class for JPA repository
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/10/2020
 */
package so.debateit.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import so.debateit.common.model.User;

public interface UserInfoRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
}
