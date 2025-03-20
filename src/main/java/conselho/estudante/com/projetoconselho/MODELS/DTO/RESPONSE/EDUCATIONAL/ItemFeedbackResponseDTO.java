package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.ItemFeedback;
import lombok.Builder;

import java.util.Date;

/**
 * Classe de resposta para a entidade {@link ItemFeedback}.
 * Fornece os dados do feedback do item no formato de resposta da API.
 */
@Builder
public record ItemFeedbackResponseDTO(
        Long id,
        Long councilId,
        Date createdAt,
        String text,
        String item
) {
}
