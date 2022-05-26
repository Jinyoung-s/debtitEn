package so.debateit.web.apis.authenticate;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import so.debateit.common.model.BoardSpecs;
import so.debateit.common.model.User;
import so.debateit.domain.application.impl.UserServiceImpl;
import so.debateit.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    UserServiceImpl userService;

    public AuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/authentication"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException, InsufficientAuthenticationException {
        log.debug("Processing login request");

        String requestBody = IOUtils.toString(request.getReader());
        LoginRequest loginRequest = null;
        try{
            loginRequest = JsonUtil.toObject(requestBody, LoginRequest.class);
            if(loginRequest == null || loginRequest.isInvalid()) {
                throw new InsufficientAuthenticationException("error.request.confirmIdPass");
            }
        } catch (Exception ex) {
            throw new InsufficientAuthenticationException("error.request.confirmIdPass");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.userId, loginRequest.userPass);
        Authentication auth = this.getAuthenticationManager().authenticate(token);
        User user = userService.getUserInfoById(loginRequest.userId);

        if(user.getUserAuth() < 1 && !BoardSpecs.RegistType.SOCIAL.getValue().equals(user.getRegiType())){
            throw new InsufficientAuthenticationException("error.request.confirmEmail");
        }

        return auth;
    }

    static class LoginRequest {
        private String userId;
        private String userPass;

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setUserPass(String userPass) {
            this.userPass = userPass;
        }

        public boolean isInvalid() {
            return StringUtils.isBlank(userId) || StringUtils.isBlank(userPass);
        }

        public String getUserId() {
            return userId;
        }

        public String getUserPass() {
            return userPass;
        }

    }

}


