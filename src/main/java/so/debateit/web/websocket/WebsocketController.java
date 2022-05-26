/**
 * The WebsocketController is for the realtime chat in the website
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/06/2022
 */
package so.debateit.web.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.DateUtils;
import so.debateit.common.model.BoardSpecs;
import so.debateit.common.model.User;
import so.debateit.domain.application.UserService;
import so.debateit.domain.model.chat.ChatInfo;
import so.debateit.infrastructure.repository.ChatInfoRepository;

@RestController
public class WebsocketController {

    @Autowired
    MessageContent messageContent;

    @Autowired
    UserService userService;

    @Autowired
    ChatInfoRepository chatInfoRepository;
    //SKIP because of server memory problem
    //ElasticServiceImpl elasticService;

    @MessageMapping("/boadMessage/{room}")
    @SendTo("/views/{room}")
    public MessageContent sendMessage(@DestinationVariable String room, BoardMessage message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        String userId = headerAccessor.getUser().getName();
        if(userId == null ) {
            throw new Exception();
        }

        if(BoardSpecs.DebateStaus.CLOSE.getValue().equals(message.getBoadMssg())){
            messageContent.setStatus(BoardSpecs.DebateStaus.CLOSE.getValue());
            return messageContent;
        }
        User chatUser = userService.getUserInfoById(userId);
        if(message.getCretUser().equals(chatUser.getUserNmbr())) {
            messageContent.setType(BoardSpecs.BoardUserType.OWNER.getValue());
        } else {
            messageContent.setType(BoardSpecs.BoardUserType.PART.getValue());
        }

        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setChatMesg(message.getBoadMssg());
        chatInfo.setChatUser(messageContent.getType());
        chatInfo.setCretDate(DateUtils.createNow().getTime());
        chatInfo.setCretUser(chatUser.getUserNmbr());
        chatInfo.setBoadNmbr(room);

        chatInfoRepository.save(chatInfo);

        /* TODO skip elasticsearch save because of server memory
        Chat chat = new Chat();
        chat.setMessage(message.getBoadMssg());
        chat.setOwner(messageContent.getType());
        chat.setCretDate(DateUtils.createNow().getTime());
        chat.setRoomNo(room);
        elasticService.saveChat(chat);
        */
        messageContent.setContent(message.getBoadMssg());

        return messageContent;
    }
}
