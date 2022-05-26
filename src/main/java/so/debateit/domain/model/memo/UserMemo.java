/**
 * The UserMemo class is for the user_memo table
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/11/2022
 */

package so.debateit.domain.model.memo;

import lombok.Getter;
import lombok.Setter;
import so.debateit.common.model.User;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="user_memo")
public class UserMemo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="memo_nmbr", nullable = false)
    Long memoNmbr;

    @Column(name = "memo_cont", nullable = false)
    String memoCont;


    @Column(name = "cret_user", nullable = false)
    Long cretUser;

    @Column(name = "cret_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date cretDate;

    @Column(name = "read_cunt")
    Long readCunt;

    @OneToOne
    @JoinColumn(name = "send_user")
    private User sendUser;

    @OneToOne
    @JoinColumn(name = "recv_user")
    private User recvUser;

}
