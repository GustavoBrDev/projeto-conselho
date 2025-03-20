package conselho.estudante.com.projetoconselho.DTO.REQUEST;

import conselho.estudante.com.projetoconselho.ENTITY.CHAT.StudentChatMessage;
import conselho.estudante.com.projetoconselho.ENTITY.USERS.Student;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de request para a entidade StudentChatMessage
 * @author Gustavo Stinghen
 * @since 17/03/2025
 * @see StudentChatMessage
 */
public record StudentChatMessageRequestDTO(
    @NotBlank
    String message,
    @NotNull
    Student student
) {

    public StudentChatMessage convert () {

        return StudentChatMessage.builder()
            .text(message)
            .sender(student)
            .build();
    }
}
