package conselho.estudante.com.projetoconselho.models.dto.request.CHAT;

import conselho.estudante.com.projetoconselho.models.dto.request.USERS.AdvisorRequestDTO;
import conselho.estudante.com.projetoconselho.models.entity.chat.AdvisorChatMessage;
import conselho.estudante.com.projetoconselho.models.entity.chat.ChatMessage;
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
    AdvisorRequestDTO advisor;

    public AdvisorChatMessage convert() {

        return AdvisorChatMessage.builder()
                .text(message)
                .advisor(advisor.convert())
                .build();
    }
}
