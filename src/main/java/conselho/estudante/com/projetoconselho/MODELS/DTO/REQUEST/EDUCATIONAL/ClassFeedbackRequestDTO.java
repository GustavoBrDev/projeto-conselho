package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.ADMINISTRATION.ClasseRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.ClassFeedback;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Date;

/**
 * Classe de requisição para criação e atualização de {@link ClassFeedback}.
 * Contém os dados necessários para manipular feedbacks de turma.
 * @author Camilly Chelest
 * @since 20/03/2025
 */
@Builder
public record ClassFeedbackRequestDTO(
        @NotNull CouncilRequestDTO councilRequestDTO,
        @NotNull ClasseRequestDTO classeRequestDTO,
        @NotNull Date createdAt,
        @NotBlank String text
) {
    public ClassFeedback convert() {
        return ClassFeedback.builder()
                .council(councilRequestDTO.convert())
                .classe(classeRequestDTO.convert())
                .createdAt(this.createdAt)
                .text(this.text)
                .build();
    }
}
