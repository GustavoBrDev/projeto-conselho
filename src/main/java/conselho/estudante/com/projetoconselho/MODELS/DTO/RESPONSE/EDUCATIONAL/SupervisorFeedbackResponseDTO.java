package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.SupervisorFeedback;
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
        CouncilResponseDTO council,
        Long supervisorId,
        Date createdAt,
        String strengthsText,
        String weaknessesText,
        String suggestionsText
) {

    public SupervisorFeedback convert() {
        return SupervisorFeedback.builder()
                .id(this.id)
                .councilId(this.councilId)
                .supervisorId(this.supervisorId)
                .createdAt(this.createdAt)
                .strengthsText(this.strengthsText)
                .weaknessesText(this.weaknessesText)
                .suggestionsText(this.suggestionsText)
                .build();
    }
}
