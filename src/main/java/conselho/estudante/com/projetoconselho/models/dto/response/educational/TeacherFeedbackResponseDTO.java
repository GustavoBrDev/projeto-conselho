package conselho.estudante.com.projetoconselho.models.dto.response.educational;

import conselho.estudante.com.projetoconselho.models.dto.response.administration.SubjectResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.educational.TeacherFeeback;
import lombok.Builder;

import java.util.Date;

/**
 * Classe de resposta para a entidade {@link TeacherFeeback}.
 * Fornece os dados do feedback do professor no formato de resposta da API.
 */
@Builder
public record TeacherFeedbackResponseDTO(
        Long id,
        CouncilResponseDTO council,
        Long teacherId,
        Date createdAt,
        String strengthsText,
        String weaknessesText,
        String suggestionsText,
        SubjectResponseDTO subject
) {

}
