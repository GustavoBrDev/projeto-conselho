package conselho.estudante.com.projetoconselho.models.dto.response.educational;

import conselho.estudante.com.projetoconselho.models.entity.administration.Classe;
import conselho.estudante.com.projetoconselho.models.entity.educational.ClassFeedback;
import conselho.estudante.com.projetoconselho.models.entity.educational.Council;
import conselho.estudante.com.projetoconselho.models.entity.educational.RepresentativePreCouncil;
import conselho.estudante.com.projetoconselho.models.entity.users.Advisor;
import lombok.Builder;

import java.util.Date;

/**
 * Data Transfer Object (DTO) para resposta contendo dados de um {@link Council}.
 *
 * @author joana voigt
 * @since 20/03/2025
 *
 * @see Classe
 * @see ClassFeedback
 * @see RepresentativePreCouncil
 * @see Advisor
 */
@Builder
public record CouncilResponseDTO(
    Long id,
    Classe classe,
    Date createdAt,
    Date date,
    Advisor advisor,
    RepresentativePreCouncil representativePreCouncil,
    ClassFeedback classFeedback,
    Boolean representativePreCouncilFinished,
    Boolean teacherPreCouncilFinished,
    Boolean representativePreCouncilStarted,
    Boolean teacherPreCouncilStarted,
    Boolean councilFinished,
    Boolean feedbackDelivered
) {
}
