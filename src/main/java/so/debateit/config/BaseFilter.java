/**
 * This spring filter used for pre-handling of the request
 *
 * @author  Jinyoung So
 * @version 1.1
 * @since   5/02/2022
 *
 * 5/25/2022 add the redirect functionality
 */
package so.debateit.config;

import org.springframework.web.filter.GenericFilterBean;
import so.debateit.util.DebateEnvironment;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class BaseFilter extends GenericFilterBean{

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long start = new Date().getTime();

        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        // TODO making the filter for the CORS
        // redirect www domain to non-www domain
        if (req.getServerName().equals(DebateEnvironment.redirectFromUrl)) {
            res.sendRedirect(DebateEnvironment.redirectToUrl+req.getRequestURI());
        }else{
            chain.doFilter(request, response);
        }

        long elapsed = new Date().getTime() - start;
        logger.debug("Request [uri =" + req.getRequestURI()+", method= "+ req.getMethod() + "] completed in " + elapsed+ " ms");
    }
}
