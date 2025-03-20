package conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ChatResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Classe model da entidade StudentResponseMessage ( mensagem de chat de estudantes )
 * @author Gustavo Stinghen
 * @since 17/03/2025
 * @see ChatMessage
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class StudentResponseMessage implements ChatMessage {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;

    private String text;

    @OneToOne
    private Student receiver;

    private Instant timestamp;

    private Boolean isRead;

    private Boolean isDeleted;

    private Instant deletedAt;

    /**
     * Método para converter um StudentResponseMessage para um ChatResponseDTO
     * @return ChatResponseDTO
     */
    public ChatResponseDTO convert () {

        return ChatResponseDTO.builder()
                .message(text)
                .isRead(isRead)
                .isDeleted(isDeleted)
                .deletedAt(deletedAt)
                .build();
    }
}
