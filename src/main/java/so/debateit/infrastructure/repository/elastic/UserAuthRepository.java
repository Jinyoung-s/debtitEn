package so.debateit.infrastructure.repository.elastic;

import org.springframework.data.jpa.repository.JpaRepository;
import so.debateit.domain.model.user.UserAuth;

public interface UserAuthRepository extends JpaRepository<UserAuth, String> {
}
