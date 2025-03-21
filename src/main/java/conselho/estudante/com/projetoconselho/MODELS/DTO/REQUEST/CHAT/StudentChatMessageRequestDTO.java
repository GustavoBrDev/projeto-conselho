package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.CHAT;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.ChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.StudentChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de request para a entidade StudentChatMessage
 * @author Gustavo Stinghen
 * @since 17/03/2025
 * @see StudentChatMessage
 */
public class StudentChatMessageRequestDTO implements ChatMessage {
    @NotBlank
    String message;
    @NotNull
    Student student;

    public StudentChatMessage convert() {

        return StudentChatMessage.builder()
                .text(message)
                .sender(student)
                .build();
    }
}
