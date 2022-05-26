/**
 * The Board class is for JPA entity of the boad_info table.
 * @author  Jinyoung So
 * @version 1.2
 * @since   5/01/2022
 *
 * 5/08/2022 : this table has a one to one relation with user_info
 * 5/09/2022 : this table has a one to many relation with boad_cmnt
 */
package so.debateit.domain.model.board;

import lombok.Getter;
import lombok.Setter;
import so.debateit.common.model.User;
import so.debateit.domain.model.chat.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name="boad_info")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="boad_nmbr", nullable = false)
    Long boadNmbr;

    @Column(name = "boad_titl", nullable = false)
    String boadTitl;

    @Column(name = "boad_cont", nullable = false)
    String boadCont;

    @Column(name = "entr_user")
    Long entrUser;

    @Column(name = "read_cunt")
    Long readCunt;

    @Column(name = "agre_cunt")
    Long agreCunt;

    @Column(name = "oppo_cunt")
    Long oppoCunt;

    @Column(name = "poin_amnt")
    Long poinAmnt;

    @Column(name = "boad_dvsn")
    String boadDvsn;

    @Column(name = "totl_cunt")
    Long totlCunt;

    @Column(name = "cret_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date cretDate;

    @Column(name = "updt_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date updtDate;

    @Column(name = "updt_user")
    Long updtUser;

    @Column(name = "clos_Time")
    @Temporal(TemporalType.TIMESTAMP)
    Date closTime;

    @Column(name = "wina_like")
    Long winaLike;

    @Column(name = "sumn_path")
    String sumnPath;

    @OneToOne
    @JoinColumn(name = "cret_user")
    private User firstUser;

    @OneToOne
    @JoinColumn(name = "part_user")
    private User secondUser;


    @OneToOne
    @JoinColumn(name = "wina_nmbr")
    private User winUser;

    @Column(name = "boad_kind")
    String boadKind;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="boad_nmbr")
    private List<Comment> commentList = new ArrayList<>();

    public Board(String boadTitl,
                 String boadCont,
                 Long entrUser,
                 Long readCunt,
                 Long agreCunt,
                 Long oppoCunt,
                 Long poinAmnt,
                 Long cretUser,
                 Date cretDate,
                 Date updtDate,
                 Long updtUser) {
        this.boadTitl = boadTitl;
        this.boadCont = boadCont;
        this.entrUser = entrUser;
        this.readCunt = readCunt;
        this.agreCunt = agreCunt;
        this.oppoCunt = oppoCunt;
        this.poinAmnt = poinAmnt;
        this.cretDate = cretDate;
        this.updtDate = updtDate;
        this.updtUser = updtUser;
    }

    public Board() {

    }
 }
