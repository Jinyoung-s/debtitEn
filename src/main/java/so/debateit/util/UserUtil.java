package so.debateit.util;

import org.springframework.security.core.context.SecurityContextHolder;
import so.debateit.common.model.User;
import so.debateit.domain.model.user.SessionUser;

public class UserUtil {

    public static User getUser() {

        User user = new User();

        if(SecurityContextHolder.getContext().getAuthentication() != null  && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            SessionUser sessionUser = (SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user.setUserId(sessionUser.getUsername());
            user.setUserNmbr(sessionUser.getUserNmbr());
        }

        return user;
    }

}
