package conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ChatMessageResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Technique;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Classe model da entidade TechniqueChatMessage ( mensagem de chat de tecnicos )
 * @author Gustavo Stinghen
 * @since 24/03/2025
 * @see ChatMessage
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class TechniqueChatMessage implements ChatMessage {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;

    private String text;

    @OneToOne
    private Technique technique;

    private Instant timestamp;

    private Boolean isRead;

    private Boolean isDeleted;

    private Instant deletedAt;

    /**
     * Método para converter um StudentChatMessage para um ChatMessageResponseDTO
     * @return ChatMessageResponseDTO
     */
    public ChatMessageResponseDTO convert () {

        return ChatMessageResponseDTO.builder()
            .message(text)
            .isRead(isRead)
            .isDeleted(isDeleted)
            .deletedAt(deletedAt)
            .build();
    }
}
