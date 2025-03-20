package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.StudentChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.StudentResponseMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de request para a entidade StudentChatMessage
 * @author Gustavo Stinghen
 * @since 17/03/2025
 * @see StudentChatMessage
 */
public record StudentResponseRequestDTO(
    @NotBlank
    String message,
    @NotNull
    Student student
) {

    public StudentResponseMessage convert () {

        return StudentResponseMessage.builder()
            .text(message)
            .receiver(student)
            .build();
    }
}
