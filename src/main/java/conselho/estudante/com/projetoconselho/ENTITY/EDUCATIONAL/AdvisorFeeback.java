package conselho.estudante.com.projetoconselho.ENTITY.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.ENTITY.USERS.Advisor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
public class AdvisorFeeback {

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
}
