package MODELS.ENTITY.EDUCATIONAL;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe model da entidade Feedback de item
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see Feedback
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ItemFeedback extends Feedback {

    private String text;

    private String item;
}
