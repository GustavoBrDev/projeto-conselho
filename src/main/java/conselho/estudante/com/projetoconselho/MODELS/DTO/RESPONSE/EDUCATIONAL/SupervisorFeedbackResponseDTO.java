package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL;

import lombok.Builder;

import java.util.Date;

/**
 * Classe de resposta para a entidade SupervisorFeedback
 * Fornece os dados do feedback do supervisor no formato de resposta da API.
 * @author Camilly Chelest
 * @since 19/03/2025
 */
@Builder
public record SupervisorFeedbackResponseDTO(

        Long id,
        Long councilId,
        Long supervisorId,
        Date createdAt,
        String strengthsText,
        String weaknessesText,
        String suggestionsText
) {
}
