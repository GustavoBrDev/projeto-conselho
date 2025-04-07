package conselho.estudante.com.projetoconselho.models.entity.educational;

import conselho.estudante.com.projetoconselho.models.dto.response.EDUCATIONAL.TeacherFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Subject;
import conselho.estudante.com.projetoconselho.models.entity.users.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
 *
 * Atualizado em 26/03/2025
 * Adicionado matéria associada ao feedback
 * @author Gustavo Stinghen
 * @see Subject
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class TeacherFeeback implements Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private Council council;

    @ManyToOne
    private Subject subject;

    @Column(nullable = false)
    private Date createdAt;

    private String strengthsText;

    private String weaknessesText;

    private String suggestionsText;

    public TeacherFeedbackResponseDTO convert() {
        return TeacherFeedbackResponseDTO.builder()
                .id(this.id)
                .council(this.council.toDTO())
                .teacherId(this.teacher.getId())//.convert()
                .subject(this.subject.toDTO())
                .createdAt(this.createdAt)
                .strengthsText(this.strengthsText)
                .weaknessesText(this.weaknessesText)
                .suggestionsText(this.suggestionsText)
                .build();
    }
}
