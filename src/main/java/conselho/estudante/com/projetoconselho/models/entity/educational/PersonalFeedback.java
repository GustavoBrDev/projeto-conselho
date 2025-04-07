package conselho.estudante.com.projetoconselho.models.entity.educational;

import conselho.estudante.com.projetoconselho.models.dto.response.EDUCATIONAL.PersonalFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.users.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

@Builder
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

    public PersonalFeedbackResponseDTO convert() {
        return PersonalFeedbackResponseDTO.builder()
                .id(this.id)
                .student(this.student.convert())
                .council(this.council.toDTO())
                .createdAt(this.createdAt)
                .text(this.text)
                .build();
    }
}