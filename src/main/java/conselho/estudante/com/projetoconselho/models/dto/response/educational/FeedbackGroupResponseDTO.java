package conselho.estudante.com.projetoconselho.models.dto.response.educational;

import conselho.estudante.com.projetoconselho.models.entity.educational.FeedbackGroup;
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
        PersonalFeedbackResponseDTO personalFeedback,
        ClassFeedbackResponseDTO classFeedback
) {
}
