package so.debateit.web.apis.authenticate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import so.debateit.util.JsonUtil;
import so.debateit.web.model.ApiResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimpleAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        ApiResult failure;
        if (exception instanceof BadCredentialsException) {
            failure = ApiResult.message("error.request.confirmIdPass");
        } else if (exception instanceof InsufficientAuthenticationException) {
            if(StringUtils.isNotEmpty(exception.getMessage())){
                failure = ApiResult.message(exception.getMessage());
            } else {
                failure = ApiResult.message("Invalid authentication request");
            }
        } else {
            failure = ApiResult.message("Authentication failure");
        }
        JsonUtil.write(response.getWriter(), failure);
    }
}