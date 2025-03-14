package MODELS.ENTITY.EDUCATIONAL;

import MODELS.ENTITY.USERS.Advisor;
import MODELS.ENTITY.USERS.Supervisor;
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
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class SupervisorFeedback {

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
