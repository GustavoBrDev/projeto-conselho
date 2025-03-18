package MODELS.DTO.REQUEST;

import MODELS.ENTITY.CHAT.StudentChatMessage;
import MODELS.ENTITY.CHAT.StudentResponseMessage;
import MODELS.ENTITY.CHAT.TeacherChatMessage;
import MODELS.ENTITY.CHAT.TeacherResponseMessage;
import MODELS.ENTITY.USERS.Student;
import MODELS.ENTITY.USERS.Teacher;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de request para a entidade TeacherResponseMessage
 * @author Gustavo Stinghen
 * @since 17/03/2025
 * @see TeacherResponseMessage
 */
public record TeacherResponseRequestDTO(
    @NotBlank
    String message,
    @NotNull
    Teacher teacher
) {

    public TeacherResponseMessage convert () {

        return TeacherResponseMessage.builder()
            .text(message)
            .receiver(teacher)
            .build();
    }
}
