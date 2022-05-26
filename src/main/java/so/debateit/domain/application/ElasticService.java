package so.debateit.domain.application;

import so.debateit.domain.model.chat.Chat;

public interface ElasticService {
    void saveChat(Chat chat) throws Exception;
}
