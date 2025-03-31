package conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.ChatMessage;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;
/**
 * Classe model da entidade ChatMessageLogs
 * É uma subclasse de {@link Log}
 * É um documento do mongodb
 * @author Gustavo Stinghen
 * @since 17/03/2025
 * @see Log, ChatMessage
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
@Builder
public class ChatMessageLogs implements Log {

    @Id
    private String id;
    @DBRef
    private ChatMessage target;

    private String type;

    private Instant timestamp;

    @CreatedDate
    @Indexed( expireAfter = "60d" )
    private Date createdAt;
}
