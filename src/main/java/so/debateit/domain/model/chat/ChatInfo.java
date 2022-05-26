/**
 * The Board class is for JPA entity of the chat_info.
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/20/2022
 *
 */

package so.debateit.domain.model.chat;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="chat_info")
public class ChatInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chat_nmbr", nullable = false)
    Long chatNmbr;

    @Column(name = "boad_nmbr", nullable = false)
    String boadNmbr;

    @Column(name = "chat_mesg")
    String chatMesg;

    @Column(name = "chat_user", nullable = false)
    String chatUser;

    @Column(name = "cret_user", nullable = false)
    Long cretUser;

    @Column(name = "cret_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date cretDate;

}
