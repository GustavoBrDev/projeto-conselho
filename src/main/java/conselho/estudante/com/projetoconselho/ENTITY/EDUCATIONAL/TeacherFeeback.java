package conselho.estudante.com.projetoconselho.ENTITY.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.ENTITY.USERS.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Classe model da entidade Feedback de melhoria de professor
 * @author Gustavo Stinghen
 * @since 13/03/2025
 * @see Teacher
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
public class TeacherFeeback implements Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private Council council;

    @Column(nullable = false)
    private Date createdAt;

    private String strengthsText;

    private String weaknessesText;

    private String suggestionsText;
}
