package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.ItemFeedback;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Date;

/**
 * Classe de requisição para criação e atualização de {@link ItemFeedback}.
 * Contém os dados necessários para manipular feedbacks de itens no conselho.
 * @author Camilly Chelest
 * @since 19/03/2025
 */
@Builder
public record ItemFeedbackRequestDTO(
        @NotNull
        Council council,
        @NotBlank
        String text,
        @NotBlank
        String item
) {
    public ItemFeedback convert() {
        return ItemFeedback.builder()
                .council(this.council)//.convert()
                .text(this.text)
                .item(this.item)
                .build();
    }
}
