package conselho.estudante.com.projetoconselho.models.dto.request.CHAT;

import conselho.estudante.com.projetoconselho.models.dto.request.USERS.StudentRequestDTO;
import conselho.estudante.com.projetoconselho.models.entity.chat.ChatMessage;
import conselho.estudante.com.projetoconselho.models.entity.chat.StudentChatMessage;
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
    StudentRequestDTO student;

    public StudentChatMessage convert() {

        return StudentChatMessage.builder()
                .text(message)
                .student(student.convert())
                .build();
    }
}
