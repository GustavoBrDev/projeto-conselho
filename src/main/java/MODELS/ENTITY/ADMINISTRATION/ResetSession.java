package MODELS.ENTITY.ADMINISTRATION;

import MODELS.ENTITY.USERS.User;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class ResetSession {

    @Id
    private String id;

    private String token;

    private User user;

    @CreatedDate
    @Indexed( expireAfter = "30m" )
    private Date createdAt;
}
