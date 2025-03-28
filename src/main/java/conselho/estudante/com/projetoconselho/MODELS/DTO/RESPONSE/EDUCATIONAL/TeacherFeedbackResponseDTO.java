package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ADMINISTRATION.SubjectResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.TeacherFeeback;
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
