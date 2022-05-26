package so.debateit.infrastructure.repository.elastic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import so.debateit.domain.model.chat.Chat;
import so.debateit.domain.model.user.UserImg;

//@Repository
public interface ElasticUserImgRepository {// extends PagingAndSortingRepository<UserImg, Long> {
    Page<Chat> searchSimilar(UserImg entity, String[] fields, Pageable pageable);

}
