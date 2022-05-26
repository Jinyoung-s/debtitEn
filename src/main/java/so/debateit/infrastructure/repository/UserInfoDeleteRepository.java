/**
 * The UserInfoDeleteRepository class for JPA repository
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/21/2020
 */
package so.debateit.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import so.debateit.domain.model.user.UserDelete;

public interface UserInfoDeleteRepository extends JpaRepository<UserDelete, Long> {
    UserDelete findByUserId(String userId);
}
