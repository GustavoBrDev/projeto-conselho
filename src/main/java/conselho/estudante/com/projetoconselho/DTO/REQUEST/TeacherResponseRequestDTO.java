<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/DTO/REQUEST/TeacherResponseRequestDTO.java
package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.CHAT.TeacherResponseMessage;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
========
package conselho.estudante.com.projetoconselho.DTO.REQUEST;

import conselho.estudante.com.projetoconselho.ENTITY.CHAT.TeacherResponseMessage;
import conselho.estudante.com.projetoconselho.ENTITY.USERS.Teacher;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/DTO/REQUEST/TeacherResponseRequestDTO.java
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
