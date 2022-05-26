/**
 * The BoardMessage class is for JPA entity of the BOAD_MSSG table.
 * this entity is intended to record of likes button that user clicked
 * @author  Jinyoung So
 * @version 1.1
 * @since   5/05/2022
 * history
 * 5/15/2022 : this class would be not used anymore and be deleted later
 */

package so.debateit.domain.model.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="BOAD_MSSG")
public class BoardMessage {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name= "chat_id")
    private Long chatId;

}
