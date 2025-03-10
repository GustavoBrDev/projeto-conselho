package MODELS.ENTITY.EDUCATIONAL;

import MODELS.ENTITY.USERS.User;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe model da entidade Feedback de melhoria
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see Feedback
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class ImprovementFeedback extends Feedback {

    private User user;

    private String strengthsText;

    private String weaknessesText;

    private String suggestionsText;
}
