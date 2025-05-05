package conselho.estudante.com.projetoconselho.models.dto.request.educational;

import conselho.estudante.com.projetoconselho.models.entity.administration.Classe;
import conselho.estudante.com.projetoconselho.models.entity.educational.*;
import conselho.estudante.com.projetoconselho.models.entity.users.Advisor;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Date;
import java.util.List;

/**
 * Record para encapsular os dados necessários para criar ou atualizar um {@link Council}.
 *
 * @author joana voigt
 * @since 2o/03/2025
 *
 * @see Classe
 * @see Council
 * @see Advisor
 * @see RepresentativePreCouncil
 * @see ClassFeedback
 */
@Builder
public record CouncilRequestDTO(
        @NotNull
        Classe classe,
        @NotNull
        Date date,
        @NotNull
        Advisor advisor,
        @NotNull
        List<AvaliableTeacher> teachers

) {

    /**
     * Converte este DTO em uma entidade {@link Council}.
     *
     * @return uma nova instância de {@link Council}.
     */
    public Council convert() {
        return Council.builder()
                .classe(classe)
                .date(date)
                .advisor(advisor)
                .teachers(teachers)
                .build();
    }
}
