package so.debateit.web.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import so.debateit.common.model.BoardSpecs;
import so.debateit.common.model.User;
import so.debateit.domain.application.UserService;
import so.debateit.domain.model.user.SessionUser;
import so.debateit.util.DebateEnvironment;
import so.debateit.web.model.UserParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class RegistrationApiController {

    @Autowired
    private UserService service;

    @PostMapping("/api/register")
    @ResponseBody
    public User register(@RequestBody User user, HttpServletRequest request) throws Exception {
        service.register(user);
        return user;
    }


    @PostMapping("/api/socialUserInfo")
    @ResponseBody
    public User socialUserInfo(@RequestBody UserParam user, HttpServletRequest request) {
        return service.getSocialUserInfo(user);
    }

    @PostMapping("/api/exsitSocialUserInfo")
    @ResponseBody
    public User exsitSocialUserInfo(@RequestBody User user, HttpServletRequest request) {
        SessionUser sessionUser = new SessionUser(user);
        return service.getUserSignUpInfo(sessionUser);
    }

    @PostMapping("/api/socialLogin")
    @ResponseBody
    public User socialLogin(@RequestBody User user, HttpServletRequest request) {
        SessionUser sessionUser = new SessionUser(user);
        List<GrantedAuthority> roles = new ArrayList<>(1);
        roles.add(new SimpleGrantedAuthority("ROLE_GUEST"));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(sessionUser, null, roles);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(token);
        return user;
    }

    @PostMapping("/api/socialRegister")
    @ResponseBody
    public User socialRegister(@RequestBody User user, HttpServletRequest request) throws Exception {
        String userPass = user.getUserPass();
        SessionUser sessionUser = new SessionUser(user);
        User search = service.getUserSignUpInfo(sessionUser);
        if(search == null) {
            user.setRegiType(BoardSpecs.RegistType.SOCIAL.getValue());
            service.register(user);
        }

        // to-do think about progress
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUserId(), userPass);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(token);
        HttpSession session = request.getSession(true);
        session.setAttribute("userid", user.getUserId());
        session.setAttribute("userNick", user.getUserNick());
        return user;
    }

    // google login
    @RequestMapping(value = "/api/getGoogleAuthUrl")
    public @ResponseBody String getGoogleAuthUrl(HttpServletRequest request) throws Exception {
        String reqUrl = DebateEnvironment.googleLoginUrl + "/o/oauth2/v2/auth?client_id=" + DebateEnvironment.googleClientId + "&redirect_uri=" + DebateEnvironment.googleRedirectUrl
                + "&response_type=code&scope=email%20profile%20openid&access_type=offline";
        return reqUrl;
    }


    @RequestMapping(value = "/user/registerEmail", method= RequestMethod.GET)
    public String emailConfirm(@RequestParam("memberEmail") String memberEmail, @RequestParam("key") String key,  Model model)throws Exception{
        service.memberAuth(memberEmail, key);
        model.addAttribute("memberEmail", memberEmail);
        return "registerEmail";
    }
}
