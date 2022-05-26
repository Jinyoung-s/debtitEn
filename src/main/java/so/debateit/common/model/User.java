package so.debateit.common.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import so.debateit.util.DebateEnvironment;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name="user_info")
public class User {
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

    @Column(name = "user_imag", nullable = false)
    String userImag;

    @Column(name = "user_poin", nullable = false)
    Long userPoin;

    @Column(name = "user_auth", nullable = false)
    Long userAuth;

    @Column(name = "regi_type")
    String regiType;

    public User() {

    }


    public String getUserImgPath () {
        //To-do properties modify
        if(StringUtils.isEmpty(this.userImag)) {
            return  DebateEnvironment.uploadImgServer + DebateEnvironment.defaultImg;
        }
        return DebateEnvironment.uploadImgServer  + this.userImag;
    }
 }
