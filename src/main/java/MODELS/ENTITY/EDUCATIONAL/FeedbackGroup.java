package MODELS.ENTITY.EDUCATIONAL;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * Classe model da entidade Feedback de grupo
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see Feedback, PersonalFeedback, ClassFeedback
 */
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
public class FeedbackGroup extends Feedback{

    private Date date;

    @OneToOne
    private PersonalFeedback personalFeedback;

    @ManyToOne
    private ClassFeedback classFeedback;
}
