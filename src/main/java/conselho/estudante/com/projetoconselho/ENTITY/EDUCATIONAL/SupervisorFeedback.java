<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/ENTITY/EDUCATIONAL/SupervisorFeedback.java
package conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Supervisor;
========
package conselho.estudante.com.projetoconselho.ENTITY.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.ENTITY.USERS.Supervisor;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/ENTITY/EDUCATIONAL/SupervisorFeedback.java
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Classe model da entidade Feedback de melhoria de supervisor
 * @author Gustavo Stinghen
 * @since 13/03/2025
 * @see Supervisor
 *
 * Atualizado em 17/03/2025
 * Adicionado uma interface de feedback
 * @author Gustavo Stinghen
 * @see Feedback
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class SupervisorFeedback implements Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date createdAt;

    @ManyToOne
    private Council council;

    @ManyToOne
    private Supervisor supervisor;

    private String strengthsText;

    private String weaknessesText;

    private String suggestionsText;
}
