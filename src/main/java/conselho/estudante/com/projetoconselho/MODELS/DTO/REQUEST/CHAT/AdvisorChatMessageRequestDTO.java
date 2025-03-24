package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.CHAT;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.AdvisorChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.ChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.StudentChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Advisor;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de request para a entidade AdvisorChatMessage
 * @author Gustavo Stinghen
 * @since 24/03/2025
 * @see AdvisorChatMessage
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class AdvisorChatMessageRequestDTO implements ChatMessage {
    @NotBlank
    String message;
    @NotNull
    Advisor advisor;

    public AdvisorChatMessage convert() {

        return AdvisorChatMessage.builder()
                .text(message)
                .advisor(advisor)
                .build();
    }
}
