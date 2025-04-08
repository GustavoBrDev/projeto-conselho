package conselho.estudante.com.projetoconselho.models.dto.request.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.models.dto.request.USERS.SupervisorRequestDTO;
import conselho.estudante.com.projetoconselho.models.entity.educational.SupervisorFeedback;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Date;

/**
 * Classe de requisição para criação e atualização de {@link SupervisorFeedback}.
 * @author Camilly Chelest
 * @since 19/03/2025
 */
@Builder
public record SupervisorFeedbackRequestDTO(
        @NotNull
        CouncilRequestDTO council,
        @NotNull
        SupervisorRequestDTO supervisor,
        @NotNull
        Date createdAt,
        @NotBlank
        String strengthsText,
        @NotBlank
        String weaknessesText,
        @NotBlank
        String suggestionsText
) {
    public SupervisorFeedback convert() {
        return SupervisorFeedback.builder()
                .council(this.council.convert())
                .supervisor(this.supervisor.convert())
                .createdAt(this.createdAt)
                .strengthsText(this.strengthsText)
                .weaknessesText(this.weaknessesText)
                .suggestionsText(this.suggestionsText)
                .build();
    }
}
