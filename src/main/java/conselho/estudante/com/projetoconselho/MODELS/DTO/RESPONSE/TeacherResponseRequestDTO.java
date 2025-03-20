package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.TeacherResponseMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
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
