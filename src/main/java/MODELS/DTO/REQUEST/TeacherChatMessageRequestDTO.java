package MODELS.DTO.REQUEST;

import MODELS.ENTITY.CHAT.StudentChatMessage;
import MODELS.ENTITY.CHAT.TeacherChatMessage;
import MODELS.ENTITY.USERS.Student;
import MODELS.ENTITY.USERS.Teacher;
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
