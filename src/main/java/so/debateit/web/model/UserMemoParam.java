package so.debateit.web.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserMemoParam {
    private Long memoNmbr;

    private String memoCont;

    private Long cretUser;

    private Date cretDate;

    private Long sendUser;

    private Long recvUser;

    private int pageNo;
}
