
package so.debateit.domain.service;

import org.springframework.stereotype.Component;
import org.thymeleaf.util.DateUtils;
import so.debateit.common.model.User;
import so.debateit.domain.common.security.PasswordEncrypt;
import so.debateit.infrastructure.repository.UserRepository;
import so.debateit.util.DebateEnvironment;

/*
 * User Registration domain Service
 */
@Component
public class RegistrationManagement {

    private UserRepository repository;
    private PasswordEncrypt passwordEncrypt;

    public RegistrationManagement(UserRepository repository, PasswordEncrypt passwordEncrypt) {
        this.repository = repository;
        this.passwordEncrypt = passwordEncrypt;
    }


    public User register(User user) {
        User existngUser = repository.findByUserId(user.getUserId());
        user.setUserPass(passwordEncrypt.encrypt(user.getUserPass()));
        user.setCretDate(DateUtils.createNow().getTime());
        user.setUserImag(DebateEnvironment.defaultImg);
        user.setUserPoin(0L);
        repository.save(user);
        return user;
    }

    public User resetPass(User user) {
        user.setUserPass(passwordEncrypt.encrypt(user.getUserPass()));
        repository.save(user);
        return user;
    }


}
