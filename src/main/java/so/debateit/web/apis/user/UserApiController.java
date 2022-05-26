/**
 * The UserApiController is the controller for features about user
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/02/2022
 */
package so.debateit.web.apis.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import so.debateit.common.model.User;
import so.debateit.domain.application.UserService;
import so.debateit.domain.model.memo.UserMemo;
import so.debateit.domain.model.user.SessionUser;
import so.debateit.web.model.UserMemoParam;
import so.debateit.web.model.UserParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/getUserInfo")
    @ResponseBody
    public UserParam getUserInfo(@AuthenticationPrincipal SessionUser user) {
        if(user == null) {
            return null;
        }

        return userService.getUserInfoAndMemo(user);
    }

    @PostMapping("/modifyUserInfo")
    @ResponseBody
    public User modifyUserInfo(@RequestBody User user, @AuthenticationPrincipal SessionUser sessionUser) {
        if(sessionUser == null) {
            return null;
        }
        return userService.modifyUserInfo(user);
    }

    @PostMapping("/logout")
    @ResponseBody
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.isAuthenticated()) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

    @PostMapping("/sendMemo")
    @ResponseBody
    public UserMemo sendMemo(@RequestBody UserMemoParam memo, @AuthenticationPrincipal SessionUser sessionUser) {
        if(sessionUser == null) {
            return null;
        }
        return userService.sendMemo(memo);
    }

    @PostMapping("/getUserMemoList")
    @ResponseBody
    public List<UserMemo> getUserMemoList(UserMemoParam param, @AuthenticationPrincipal SessionUser sessionUser) {
        if(sessionUser == null) {
            return null;
        }
        return userService.getUserMemoList(param, sessionUser);
    }

    @PostMapping("/userMemoCheck")
    @ResponseBody
    public void userMemoCheck(@RequestBody UserMemo memo){
        userService.userMemoCheck(memo);
    }


    @PostMapping("/chagnePassword")
    @ResponseBody
    public User chagnePassword(@RequestBody UserParam user, @AuthenticationPrincipal SessionUser sessionUser) {
        if(sessionUser == null) {
            return null;
        }
        return userService.changePassword(user);
    }

    @PostMapping("/withdrawal")
    @ResponseBody
    public void withdrawal(@RequestBody User user, @AuthenticationPrincipal SessionUser sessionUser) {
        if(sessionUser == null) {
            return;
        }

        userService.deleteUser(user);
    }
}
