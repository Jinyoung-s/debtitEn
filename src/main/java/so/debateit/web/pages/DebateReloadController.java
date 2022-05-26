/**
 * this controller is for the Vue.js router
 * to prevent reload error
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/02/2022
 */
package so.debateit.web.pages;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DebateReloadController implements ErrorController {
    @GetMapping("/error")
    public String redirectRoot() {
        return "index";
    }

    @GetMapping("/login/oauth_google")
    public String redirectGoogleAuth() {
        return "index";
    }

    public String getErrorPath() {
        return "/error";
    }
}
