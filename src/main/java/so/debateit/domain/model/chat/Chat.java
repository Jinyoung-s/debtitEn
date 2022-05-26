/**
 * The Board class is for JPA entity of the chat.
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/10/2022
 *
 * 5/20/2022 : this class changed to chat_info because of the problem about memory of Elasticsearch
 */
package so.debateit.domain.model.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@ToString
@Document(indexName = "chat")
public class Chat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Field(type = FieldType.Keyword)
    private String roomNo;

    @Field(type = FieldType.Keyword)
    private String owner;

    @Field(type = FieldType.Keyword)
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private Date cretDate;

    public Chat() {
    }

    @PersistenceConstructor
    public Chat(String id, String owner, String message, Date cretDate) {
        this.id = id;
        this.owner = owner;
        this.message = message;
        this.cretDate = cretDate;
    }
}
