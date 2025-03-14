package MODELS.ENTITY.EDUCATIONAL;

import MODELS.ENTITY.USERS.Advisor;
import MODELS.ENTITY.USERS.Teacher;
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
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TeacherFeeback {

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
