<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/ENTITY/EDUCATIONAL/FeedbackGroup.java
package conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL;
========
package conselho.estudante.com.projetoconselho.ENTITY.EDUCATIONAL;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/ENTITY/EDUCATIONAL/FeedbackGroup.java

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
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
