package conselho.estudante.com.projetoconselho.models.dto.request.chat;

import conselho.estudante.com.projetoconselho.models.dto.request.users.TeacherRequestDTO;
import conselho.estudante.com.projetoconselho.models.entity.chat.ChatMessage;
import conselho.estudante.com.projetoconselho.models.entity.chat.TeacherChatMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de request para a entidade TeacherChatMessage
 * @author Gustavo Stinghen
 * @since 17/03/2025
 * @see TeacherChatMessage
 */
public class TeacherChatMessageRequestDTO implements ChatMessage {
    @NotBlank
    String message;
    @NotNull
    TeacherRequestDTO teacher;

    public TeacherChatMessage convert () {

        return TeacherChatMessage.builder()
            .text(message)
            .teacher(teacher.convert())
            .build();
    }
}
