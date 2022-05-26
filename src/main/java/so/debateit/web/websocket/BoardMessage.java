package so.debateit.web.websocket;

import lombok.Getter;
import lombok.Setter;
import org.thymeleaf.util.DateUtils;
import so.debateit.web.model.BaseResult;

import java.util.Date;

@Getter
@Setter
public class BoardMessage extends BaseResult {

    public BoardMessage(){

    }

    private String boadMssg;

    private Long cretUser;

    public Date getMssgTime() {
        return DateUtils.createNow().getTime();
    }

}
