package conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.ItemFeedbackResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Classe model da entidade Feedback de item
 * @author Gustavo Stinghen
 * @since 10/03/2025
 *
 * Atualizado em 13/03/2025
 * @author Gustavo Stinghen
 *
 * Atualizado em 17/03/2025
 * Utilização de uma interface
 * @author Gustavo Stinghen
 * @see Feedback
 */
@EqualsAndHashCode()
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class ItemFeedback implements Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date createdAt;

    @ManyToOne
    private Council council;

    private String text;

    private String item;

    public ItemFeedbackResponseDTO convert() {
        return ItemFeedbackResponseDTO.builder()
                .id(this.id)
                .createdAt(this.createdAt)
                .text(this.text)
                .item(this.item)
                .build();
    }
}
