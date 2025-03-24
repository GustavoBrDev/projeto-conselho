package conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.SupervisorFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Supervisor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
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

    public SupervisorFeedbackResponseDTO convert() {
        return SupervisorFeedbackResponseDTO.builder()
                .id(this.id)
                .councilId(this.council.getId())  //os que são objetos, tem q da um .convert(), ou getId()?
                .supervisorId(this.supervisor.convert())  //os que são objetos, tem q da um .convert(), ou getId()?
                .createdAt(this.createdAt)
                .strengthsText(this.strengthsText)
                .weaknessesText(this.weaknessesText)
                .suggestionsText(this.suggestionsText)
                .build();
    }
}
