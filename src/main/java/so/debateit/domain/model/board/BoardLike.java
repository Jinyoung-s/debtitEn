/**
 * The Board class is for JPA entity of the boad_like table.
 * this entity is intended to record of likes button that user clicked
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/12/2022
 */

package so.debateit.domain.model.board;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Entity
@Component
@Table(name="boad_like")
public class BoardLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="like_nmbr", nullable = false)
    Long likeNmbr;

    @Column(name = "user_nmbr", nullable = false)
    Long userNmbr;

    @Column(name = "boad_nmbr", nullable = false)
    Long boadNmbr;

    @Column(name = "like_type", nullable = false)
    String likeType;

    @Column(name = "cret_user", nullable = false)
    Long cretUser;

    @Column(name = "cret_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date cretDate;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public BoardLike(Long likeNmbr, Long userNmbr, Long boadNmbr, String likeType, Long cretUser, Date cretDate) {
        this.likeNmbr = likeNmbr;
        this.userNmbr = userNmbr;
        this.boadNmbr = boadNmbr;
        this.likeType = likeType;
        this.cretUser = cretUser;
        this.cretDate = cretDate;
    }

    public BoardLike() {

    }
 }
