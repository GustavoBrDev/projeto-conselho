package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.PersonalFeedback;
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
        Long studentId,
        Date createdAt,
        String text
) {
    public PersonalFeedback convert() {
        return PersonalFeedback.builder()
                .id(this.id)
                .councilId(this.councilId)
                .studentId(this.studentId)
                .createdAt(this.createdAt)
                .text(this.text)
                .build();
    }
}
