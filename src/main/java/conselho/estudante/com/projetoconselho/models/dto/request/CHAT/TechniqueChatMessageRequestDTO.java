package conselho.estudante.com.projetoconselho.models.dto.request.CHAT;

import conselho.estudante.com.projetoconselho.models.dto.request.USERS.TechniqueRequestDTO;
import conselho.estudante.com.projetoconselho.models.entity.chat.TechniqueChatMessage;
import conselho.estudante.com.projetoconselho.models.entity.chat.ChatMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de request para a entidade TechniqueChatMessage
 * @author Gustavo Stinghen
 * @since 24/03/2025
 * @see TechniqueChatMessage
 */
public class TechniqueChatMessageRequestDTO implements ChatMessage {
    @NotBlank
    String message;
    @NotNull
    TechniqueRequestDTO technique;

    public TechniqueChatMessage convert() {

        return TechniqueChatMessage.builder()
                .text(message)
                .technique(technique.convert())
                .build();
    }
}
