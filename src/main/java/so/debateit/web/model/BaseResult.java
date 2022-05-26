package so.debateit.web.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import so.debateit.common.model.User;
import so.debateit.domain.model.user.SessionUser;

@Getter
@Setter
public class BaseResult {

    private boolean successStatus = true;
    private String errMsg = "";

    @Value("${custom.defaultImg}")
    private String defaultImg;

    public User getUser() {
        User user = new User();
        if(SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            SessionUser sessionUser = (SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user.setUserId(sessionUser.getUsername());
            user.setUserNmbr(sessionUser.getUserNmbr());
            user.setUserNick(sessionUser.getUserNick());
            if(StringUtils.isEmpty(sessionUser.getUserImag())){
                user.setUserImag(defaultImg);
            } else {
                user.setUserImag(sessionUser.getUserImag());
            }

        }
        return user;
    }
}
