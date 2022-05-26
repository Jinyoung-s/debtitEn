package so.debateit.domain.application;

import org.springframework.security.core.userdetails.UserDetailsService;
import so.debateit.common.model.User;
import so.debateit.domain.model.memo.UserMemo;
import so.debateit.domain.model.user.SessionUser;
import so.debateit.web.model.UserMemoParam;
import so.debateit.web.model.UserParam;

import java.util.List;

public interface UserService extends UserDetailsService {
    void register(User user) throws Exception;

    User getUserInfo(SessionUser user);

    User getUserInfoById(String userId);

    User modifyUserInfo(User user);

    UserMemo sendMemo(UserMemoParam memo);

    List<UserMemo> getUserMemoList(UserMemoParam param, SessionUser user);

    void userPointUpdate(User user, Long point);

    UserParam getUserInfoAndMemo(SessionUser user);

    void userMemoCheck(UserMemo memo);

    User getUserSignUpInfo(SessionUser user);

    void memberAuth(String memberEmail, String authKey) throws Exception;

    User changePassword(UserParam user);

    void deleteUser(User user);

    User getSocialUserInfo(UserParam user);
}
