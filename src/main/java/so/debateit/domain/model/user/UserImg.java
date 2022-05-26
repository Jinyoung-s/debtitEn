/**
 * The UserDelete class is for userimg of elasticsearch
 *
 * @author  Jinyoung So
 * @version 1.1
 * @since   5/25/2020
 *
 * This class is deprecated and will be deleted
 */
package so.debateit.domain.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@Document(indexName = "userimg")
public class UserImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Field(type = FieldType.Keyword)
    private Long userNmbr;

    @Field(type = FieldType.Binary, index = false)
    private String userImg;

    public UserImg () {

    }

    @PersistenceConstructor
    public UserImg(String id, Long userNmbr, String userImg) {
        this.id = id;
        this.userNmbr = userNmbr;
        this.userImg = userImg;
    }
}
