package so.debateit.web.websocket;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.DateUtils;
import so.debateit.web.model.BaseResult;

import java.util.Date;

@Component
@Getter
@Setter
public class MessageContent extends BaseResult {

    private String content;

    private String type = "";

    private Date cretDate = DateUtils.createNow().getTime();

    private String status = "";

    public MessageContent(){

    }

    public MessageContent(String content) {
        this.content = content;
    }

    public String getContent(){
        return content;
    }
    }