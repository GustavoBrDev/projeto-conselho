package MODELS.ENTITY.CHAT;

import MODELS.ENTITY.USERS.Student;
import MODELS.ENTITY.USERS.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Classe model da entidade StudentChatMessage ( mensagem de chat de estudantes )
 * @author Gustavo Stinghen
 * @since 17/03/2025
 * @see ChatMessage
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class StudentChatMessage implements ChatMessage {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;

    private String text;

    @OneToOne
    private Student sender;

    private Instant timestamp;

    private Boolean isRead;

    private Boolean isDeleted;

    private Instant deletedAt;
}
