package so.debateit.domain.application.impl;

import so.debateit.domain.application.ElasticService;
import so.debateit.domain.model.chat.Chat;

public class ElasticServiceImpl implements ElasticService  {

   //@Autowired
   //ElasticChatRepository elasticChatRepository;

    //@Autowired
    //ElasticSearchRepository elasticSearchRepository;

    @Override
    public void saveChat(Chat chat) throws Exception{
        //elasticChatRepository.save(chat);
    }

}
