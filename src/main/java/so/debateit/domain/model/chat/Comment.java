/**
 * The Comment class is for JPA entity of the boad_cmnt.
 * This table store the data of comment of post
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/12/2022
 */

package so.debateit.domain.model.chat;

import lombok.Getter;
import lombok.Setter;
import so.debateit.common.model.User;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="boad_cmnt")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cmnt_nmbr", nullable = false)
    Long cmntNmbr;

    @Column(name = "user_nick")
    String userNick;

    @Column(name = "boad_nmbr")
    Long boadNmbr;

    @Column(name = "cmnt_mesg")
    String cmntMesg;

    @Column(name = "cret_user", nullable = false)
    Long cretUser;

    @Column(name = "cret_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date cretDate;

    @Column(name = "dele_dvsn")
    String deleDvsn;

    @OneToOne
    @JoinColumn(name = "user_nmbr")
    User user = new User();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parn_nmbr")
    private Comment parent;

    public Comment() {
    }

    public Comment(Long boadNmbr, String cmnt_mesg, Long cretUser, Date cretDate, User user) {
        this.boadNmbr = boadNmbr;
        this.cmntMesg = cmntMesg;
        this.cretUser = cretUser;
        this.cretDate = cretDate;
        this.user = user;
    }
}
