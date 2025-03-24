<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/ENTITY/CHAT/TeacherResponseMessage.java
package conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ChatResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
========
package conselho.estudante.com.projetoconselho.ENTITY.CHAT;

import conselho.estudante.com.projetoconselho.DTO.RESPONSE.ChatResponseDTO;
import conselho.estudante.com.projetoconselho.ENTITY.USERS.Teacher;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/ENTITY/CHAT/TeacherResponseMessage.java
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
public class TeacherResponseMessage implements ChatMessage{

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;

    private String text;

    @OneToOne
    private Teacher receiver;

    private Instant timestamp;

    private Boolean isRead;

    private Boolean isDeleted;

    private Instant deletedAt;

    /**
     * Método para converter um TeacherResponseMessage para um ChatResponseDTO
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
