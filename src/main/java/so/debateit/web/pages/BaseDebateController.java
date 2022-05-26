/**
 * base controller of debate system
 * and listing the post
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/01/2022
 */
package so.debateit.web.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseDebateController {

    @GetMapping(value={"/", "/login", "/view/*"})
    public String entry(){
        return "index";
    }

}
