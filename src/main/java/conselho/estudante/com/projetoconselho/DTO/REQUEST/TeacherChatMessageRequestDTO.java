<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/DTO/REQUEST/TeacherChatMessageRequestDTO.java
package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.TeacherChatMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
========
package conselho.estudante.com.projetoconselho.DTO.REQUEST;

import conselho.estudante.com.projetoconselho.ENTITY.CHAT.TeacherChatMessage;
import conselho.estudante.com.projetoconselho.ENTITY.USERS.Teacher;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/DTO/REQUEST/TeacherChatMessageRequestDTO.java
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
