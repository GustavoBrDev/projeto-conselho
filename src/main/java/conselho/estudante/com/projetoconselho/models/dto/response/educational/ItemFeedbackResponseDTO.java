package conselho.estudante.com.projetoconselho.models.dto.response.educational;

import conselho.estudante.com.projetoconselho.models.entity.educational.ItemFeedback;
import lombok.Builder;

import java.util.Date;

/**
 * Classe de resposta para a entidade {@link ItemFeedback}.
 * Fornece os dados do feedback do item no formato de resposta da API.
 */
@Builder
public record ItemFeedbackResponseDTO(
        Long id,
        Long councilId,
        Date createdAt,
        String text,
        String item
) {

    public ItemFeedback convert() {
        return ItemFeedback.builder()
                .id(this.id)
                .createdAt(this.createdAt)
                .text(this.text)
                .item(this.item)
                .build();
    }
}
