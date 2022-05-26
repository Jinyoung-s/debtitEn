package so.debateit.web.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class BoardLikePayLoad extends BaseResult{

    String type = "";
    Long boadNmbr;

}
