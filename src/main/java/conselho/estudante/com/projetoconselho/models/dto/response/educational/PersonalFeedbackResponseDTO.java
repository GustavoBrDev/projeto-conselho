package conselho.estudante.com.projetoconselho.models.dto.response.educational;

import conselho.estudante.com.projetoconselho.models.dto.response.users.StudentResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.educational.PersonalFeedback;
import lombok.Builder;

import java.util.Date;

/**
 * Classe de resposta para a entidade {@link PersonalFeedback}.
 * Fornece os dados do feedback pessoal no formato de resposta da API.
 * @author Camilly Chelest
 * @since 20/03/2025
 */
@Builder
public record PersonalFeedbackResponseDTO(
        Long id,
        CouncilResponseDTO council,
        StudentResponseDTO student,
        Date createdAt,
        String text
) {
}
