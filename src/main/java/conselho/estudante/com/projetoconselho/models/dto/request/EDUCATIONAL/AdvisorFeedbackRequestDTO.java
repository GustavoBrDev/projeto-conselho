package conselho.estudante.com.projetoconselho.models.dto.request.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.models.dto.request.USERS.AdvisorRequestDTO;
import conselho.estudante.com.projetoconselho.models.entity.educational.AdvisorFeeback;
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
        CouncilRequestDTO council,
        @NotNull
        AdvisorRequestDTO advisor,
        @NotNull
        Date createdAt,
        @NotBlank
        String strengthsText,
        @NotBlank
        String weaknessesText,
        @NotBlank
        String suggestionsText
) {
    public AdvisorFeeback convert() {
        return AdvisorFeeback.builder()
                .council(this.council.convert())
                .advisor(this.advisor.convert())
                .createdAt(this.createdAt)
                .strengthsText(this.strengthsText)
                .weaknessesText(this.weaknessesText)
                .suggestionsText(this.suggestionsText)
                .build();
    }
}
