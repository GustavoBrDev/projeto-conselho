package MODELS.ENTITY.EDUCATIONAL;

import MODELS.ENTITY.USERS.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Classe model da entidade Feedback pessoal
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see Student, Feedback
 *
 * Atualizado em 13/03/2025
 * @author Gustavo Stinghen
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class PersonalFeedback implements Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date createdAt;

    @ManyToOne
    private Council council;

    private String text;

    @ManyToOne
    private Student student;
}