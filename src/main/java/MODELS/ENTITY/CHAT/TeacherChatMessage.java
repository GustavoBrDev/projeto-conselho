package MODELS.ENTITY.CHAT;

import MODELS.ENTITY.USERS.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * Classe model da entidade TeacherChatMessage ( mensagem de chat de professores )
 * @author Gustavo Stinghen
 * @since 17/03/2025
 * @see ChatMessage
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TeacherChatMessage implements ChatMessage {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;

    private String text;

    @OneToOne
    private Teacher sender;

    private Instant timestamp;

    private Boolean isRead;

    private Boolean isDeleted;

    private Instant deletedAt;
}
