package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.AdvisorFeeback;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Advisor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Date;

/**
 * Classe de requisição para criação e atualização de {@link AdvisorFeeback}.
 * Contém os dados necessários para manipular feedbacks de orientadores.
 * @author Camilly Chelest
 * @since 19/03/2025
 */
@Builder
public record AdvisorFeedbackRequestDTO(
        @NotNull
        Council council,
        @NotNull
        Advisor advisor,
        @NotNull
        Date createdAt,
        @NotBlank
        String strengthsText,
        @NotBlank
        String weaknessesText,
        @NotBlank
        String suggestionsText
) {
    public AdvisorFeeback convert(Council council, Advisor advisor) {
        return AdvisorFeeback.builder()
                .council(this.council)//.convert()
                .advisor(this.advisor)
                .createdAt(this.createdAt)
                .strengthsText(this.strengthsText)
                .weaknessesText(this.weaknessesText)
                .suggestionsText(this.suggestionsText)
                .build();
    }
}
