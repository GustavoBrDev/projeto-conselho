package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.PersonalFeedback;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Date;

/**
 * Classe de requisição para criação e atualização de {@link PersonalFeedback}.
 * Contém os dados necessários para manipular feedbacks pessoais dos estudantes.
 * @Author Camilly Chelest
 * @since 20/03/2025
 */
@Builder
public record PersonalFeedbackRequestDTO(
        @NotNull Long councilId,
        @NotNull Long studentId,
        @NotNull Date createdAt,
        @NotBlank String text
) {
    public PersonalFeedback convert(Council council, Student student) {
        return PersonalFeedback.builder()
                .council(this.council)
                .student(this.student)
                .createdAt(this.createdAt)
                .text(this.text)
                .build();
    }
}
