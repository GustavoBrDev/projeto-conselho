package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.ClassFeedback;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.FeedbackGroup;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.PersonalFeedback;
import lombok.Builder;

import java.util.Date;

/**
 * DTO de resposta para representar um {@link FeedbackGroup}
 * Fornece os detalhes do grupo de feedbacks em um formato seguro para saída de API.
 * @author Camilly Chelest
 * @since 19/03/2025
 */

@Builder
public record FeedbackGroupResponseDTO(
        Long id,
        Date date,
        PersonalFeedbackResponseDTO personalFeedbackId,
        ClassFeedbackResponseDTO classFeedbackId
) {
}
