/**
 * The PasswordEncrypt class implement features for encoding the user password
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/08/2022
 */
package so.debateit.domain.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncrypt {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordEncrypt(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    public String encrypt(String pass) {
        return passwordEncoder.encode(pass);
    }
}
