package conselho.estudante.com.projetoconselho.models.entity.educational;

import conselho.estudante.com.projetoconselho.models.dto.response.EDUCATIONAL.SupervisorFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.users.Supervisor;
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
                .council(this.council.toDTO())
                .supervisor(this.supervisor.convert())
                .createdAt(this.createdAt)
                .strengthsText(this.strengthsText)
                .weaknessesText(this.weaknessesText)
                .suggestionsText(this.suggestionsText)
                .build();
    }
}
