package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ADMINISTRATION.ClasseResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.ClassFeedback;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import lombok.Builder;

import java.util.Date;

/**
 * Classe de resposta para a entidade {@link ClassFeedback}.
 * Fornece os dados do feedback de turma no formato de resposta da API.
 * @author Camilly Chelest
 * @since 20/03/2025
 */
@Builder
public record ClassFeedbackResponseDTO(
        Long id,
        CouncilResponseDTO council,
        ClasseResponseDTO classe,
        Date createdAt,
        String text
) {
}
