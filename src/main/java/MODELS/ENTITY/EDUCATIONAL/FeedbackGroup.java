package MODELS.ENTITY.EDUCATIONAL;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * Classe model da entidade Feedback de grupo
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see PersonalFeedback, ClassFeedback
 *
 * Atualizado em 13/03/2025
 * @author Gustavo Stinghen
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class FeedbackGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @OneToOne
    private PersonalFeedback personalFeedback;

    @ManyToOne
    private ClassFeedback classFeedback;
}
