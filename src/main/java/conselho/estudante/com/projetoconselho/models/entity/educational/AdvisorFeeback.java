package conselho.estudante.com.projetoconselho.models.entity.educational;

import conselho.estudante.com.projetoconselho.models.dto.response.EDUCATIONAL.AdvisorFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.users.Advisor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Classe model da entidade Feedback de melhoria de orientador
 * @author Gustavo Stinghen
 * @since 13/03/2025
 * @see Advisor
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class AdvisorFeeback implements Feedback{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Advisor advisor;

    @ManyToOne
    private Council council;

    @Column(nullable = false)
    private Date createdAt;

    private String strengthsText;

    private String weaknessesText;

    private String suggestionsText;

    public AdvisorFeedbackResponseDTO convert() {
        return AdvisorFeedbackResponseDTO.builder()
                .id(this.id)
                .council(this.council.toDTO())
                .advisor(this.advisor.convert())
                .createdAt(this.createdAt)
                .strengthsText(this.strengthsText)
                .weaknessesText(this.weaknessesText)
                .suggestionsText(this.suggestionsText)
                .build();
    }
}
