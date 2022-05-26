package so.debateit.infrastructure.repository.elastic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import so.debateit.domain.model.chat.Chat;

import java.util.List;

//@Repository
public interface ElasticChatRepository {//extends PagingAndSortingRepository<Chat, Long> {
    Page<Chat> searchSimilar(Chat entity, String[] fields, Pageable pageable);
    List<Chat> findByRoomNoOrderByCretDate(String roomNo);

}
