package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.RepresentativePreCouncil;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Advisor;
import lombok.Builder;

import java.util.Date;

@Builder
public record CouncilResponseDTO(
    Long id,
    Classe classe,
    Date createdAt,
    Date date,
    Advisor advisor,
    RepresentativePreCouncil representativePreCouncil,
    Boolean representativePreCouncilFinished,
    Boolean teacherPreCouncilFinished,
    Boolean representativePreCouncilStarted,
    Boolean teacherPreCouncilStarted,
    Boolean councilFinished,
    Boolean feedbackDelivered
) {
}
