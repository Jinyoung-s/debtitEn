/**
 * The UserAuth class is for user_auth table
 * the user_auth table is for the login process
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/18/2020
 *
 */
package so.debateit.domain.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name="user_auth")
public class UserAuth {
    @Id
    @Column(name = "user_mail", nullable = false)
    String userMail;

    @Column(name = "auth_ket", nullable = false)
    String authKet;


}
