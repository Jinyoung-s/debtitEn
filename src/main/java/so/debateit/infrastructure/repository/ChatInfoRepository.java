/**
 * The BoardRepository class for JPA repository
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/20/2022
 */

package so.debateit.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import so.debateit.domain.model.chat.ChatInfo;

import java.util.List;

public interface ChatInfoRepository extends JpaRepository<ChatInfo, Long> {
    List<ChatInfo>  findByBoadNmbr(String boadNmbr);
}
