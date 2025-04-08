package conselho.estudante.com.projetoconselho.models.dto.response.educational;

import conselho.estudante.com.projetoconselho.models.dto.response.users.AdvisorResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.educational.AdvisorFeeback;
import lombok.Builder;

import java.util.Date;

/**
 * Classe de resposta para a entidade {@link AdvisorFeeback}.
 * Fornece os dados do feedback do orientador no formato de resposta da API.
 * @author
 * @since 14/03/2025
 */
@Builder
public record AdvisorFeedbackResponseDTO(
        Long id,
        CouncilResponseDTO council,
        AdvisorResponseDTO advisor,
        Date createdAt,
        String strengthsText,
        String weaknessesText,
        String suggestionsText
) {

}
