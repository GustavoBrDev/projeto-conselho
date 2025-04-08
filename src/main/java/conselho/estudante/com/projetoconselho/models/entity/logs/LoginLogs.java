package conselho.estudante.com.projetoconselho.models.entity.logs;

import conselho.estudante.com.projetoconselho.models.entity.users.User;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Document
@Builder
@Data
public class LoginLogs {

    @Id
    private String id;

    @DBRef
    private User user;

    private Instant timestamp;
}
