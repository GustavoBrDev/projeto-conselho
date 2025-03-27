package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.TeacherFeeback;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Date;

/**
 * Classe de requisição para criação e atualização de {@link TeacherFeeback}.
 * Contém os dados necessários para manipular feedbacks de professores.
 * @author Camilly Chelest
 * @since 19/03/2025
 */
@Builder
public record TeacherFeedbackRequestDTO(
        @NotNull
        Long councilId,
        @NotNull
        Long teacherId,
        @NotNull
        Date createdAt,
        @NotBlank
        String strengthsText,
        @NotBlank
        String weaknessesText,
        @NotBlank
        String suggestionsText
) {
    public TeacherFeeback convert(Council council, Teacher teacher) {
        return TeacherFeeback.builder()
                /*.council(this.council)//.convert()
                .teacher(this.teacher)//.convert()*/
                .createdAt(this.createdAt)
                .strengthsText(this.strengthsText)
                .weaknessesText(this.weaknessesText)
                .suggestionsText(this.suggestionsText)
                .build();
    }
}
