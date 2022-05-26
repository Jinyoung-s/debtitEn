/**
 * The UserMemo class is for the poin_hist table
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/12/2022
 */
package so.debateit.domain.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="poin_hist")
public class PoinHist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="poin_nmbr", nullable = false)
    Long poinNmbr;

    @Column(name = "user_nmbr")
    Long userNmbr;

    @Column(name = "poin_amnt")
    Long poinAmnt;

    @Column(name = "cret_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date cretDate;

    @Column(name = "cret_user")
    Long cretUser;

}
