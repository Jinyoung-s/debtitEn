/**
 * The UserPoinRepository class for JPA repository
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/12/2022
 */
package so.debateit.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import so.debateit.domain.model.user.PoinHist;

public interface UserPoinRepository extends JpaRepository<PoinHist, Long> {
}
