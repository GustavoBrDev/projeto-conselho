package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.ADMINISTRATION.SubjectRequestDTO;
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
        CouncilRequestDTO council,
        @NotNull
        Long teacherId,
        @NotNull
        SubjectRequestDTO subject,
        @NotNull
        Date createdAt,
        @NotBlank
        String strengthsText,
        @NotBlank
        String weaknessesText,
        @NotBlank
        String suggestionsText
) {
    public TeacherFeeback convert() {
        return TeacherFeeback.builder()
                .council(this.council().convert())
                .subject(this.subject().convert())
                //.teacher(teacher)
                .createdAt(this.createdAt)
                .strengthsText(this.strengthsText)
                .weaknessesText(this.weaknessesText)
                .suggestionsText(this.suggestionsText)
                .build();
    }
}
