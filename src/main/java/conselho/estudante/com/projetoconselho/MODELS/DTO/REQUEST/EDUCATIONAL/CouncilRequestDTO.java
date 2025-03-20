package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.PreCouncil;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.RepresentativePreCouncil;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Advisor;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Representative;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Date;

@Builder
public record CouncilRequestDTO(
        @NotNull
        Classe classe,
        @NotNull
        Date createdAt,
        @NotNull
        Date date,
        @NotNull
        Advisor advisor,
        @NotNull
        RepresentativePreCouncil representativePreCouncil,
        @NotNull
        Boolean representativePreCouncilFinished,
        @NotNull
        Boolean teacherPreCouncilFinished,
        @NotNull
        Boolean representativePreCouncilStarted,
        @NotNull
        Boolean teacherPreCouncilStarted,
        @NotNull
        Boolean councilFinished,
        @NotNull
        Boolean feedbackDelivered

) {

    public Council convert() {
        return Council.builder()
                .classe(classe)
                .createdAt(createdAt)
                .date(date)
                .advisor(advisor)
                .representativePreCouncil(representativePreCouncil)
                .representativePreCouncilFinished(representativePreCouncilFinished)
                .teacherPreCouncilFinished(teacherPreCouncilFinished)
                .representativePreCouncilStarted(representativePreCouncilStarted)
                .teacherPreCouncilStarted(teacherPreCouncilStarted)
                .councilFinished(councilFinished)
                .feedbackDelivered(feedbackDelivered)
                .build();
    }
}
