<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/DTO/REQUEST/StudentChatMessageRequestDTO.java
package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.StudentChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
========
package conselho.estudante.com.projetoconselho.DTO.REQUEST;

import conselho.estudante.com.projetoconselho.ENTITY.CHAT.StudentChatMessage;
import conselho.estudante.com.projetoconselho.ENTITY.USERS.Student;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/DTO/REQUEST/StudentChatMessageRequestDTO.java
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
