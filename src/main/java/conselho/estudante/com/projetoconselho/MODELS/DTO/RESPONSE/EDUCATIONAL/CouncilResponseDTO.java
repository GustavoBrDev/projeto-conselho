package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.ClassFeedback;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.RepresentativePreCouncil;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Advisor;
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
