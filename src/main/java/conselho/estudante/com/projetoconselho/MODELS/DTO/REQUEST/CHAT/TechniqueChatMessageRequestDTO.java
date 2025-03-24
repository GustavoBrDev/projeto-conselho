package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.CHAT;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.TechniqueChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.ChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Technique;
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
    Technique technique;

    public TechniqueChatMessage convert() {

        return TechniqueChatMessage.builder()
                .text(message)
                .technique(technique)
                .build();
    }
}
