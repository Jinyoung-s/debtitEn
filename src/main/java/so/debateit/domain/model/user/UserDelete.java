/**
 * The UserDelete class is for user_info_delete
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/21/2020
 *
 */
package so.debateit.domain.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name="user_info_delete")
public class UserDelete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_nmbr", nullable = false)
    Long userNmbr;

    @Column(name = "user_id", nullable = false)
    String userId;

    @Column(name = "user_nick", nullable = false)
    String userNick;

    @Column(name = "user_pass", nullable = false)
    String userPass;

    @Column(name = "cret_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date cretDate;

    @Column(name = "user_imag")
    String userImag;

    @Column(name = "user_poin")
    Long userPoin;

    @Column(name = "user_auth")
    Long userAuth;

    @Column(name = "regi_type")
    String regiType;

    public UserDelete() {

    }
 }
