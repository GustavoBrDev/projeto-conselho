package conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Document
public class LoginLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private User user;

    private Instant timestamp;
}
