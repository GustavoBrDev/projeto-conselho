package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.TeacherChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de request para a entidade TeacherChatMessage
 * @author Gustavo Stinghen
 * @since 17/03/2025
 * @see TeacherChatMessage
 */
public record TeacherChatMessageRequestDTO(
    @NotBlank
    String message,
    @NotNull
    Teacher teacher
) {

    public TeacherChatMessage convert () {

        return TeacherChatMessage.builder()
            .text(message)
            .sender(teacher)
            .build();
    }
}
