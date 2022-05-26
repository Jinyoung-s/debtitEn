package so.debateit.web.model;

import lombok.Getter;
import lombok.Setter;
import so.debateit.common.model.User;
import so.debateit.domain.model.memo.UserMemo;

import java.util.List;

@Getter
@Setter
public class UserParam {
    private User user;

    private List<UserMemo> userMemo;

    private String userNewPass;

    private String usertoken;

    private String userSocialCode;

}
