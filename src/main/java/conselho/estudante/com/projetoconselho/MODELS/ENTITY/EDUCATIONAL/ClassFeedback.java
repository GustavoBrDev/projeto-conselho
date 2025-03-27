package conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.ClassFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Classe model da entidade Feedback de turma
 * @author Gustavo Stinghen
 * @since 13/03/2025
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class ClassFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date createdAt;

    @ManyToOne
    private Council council;

    @ManyToOne
    private Classe classe;

    @Column(nullable = false)
    private String text;

    public ClassFeedbackResponseDTO convert() {
        return ClassFeedbackResponseDTO.builder()
                .id(id)
                .councilId(council.getId())
                .classId(classe.getId())
                .createdAt(createdAt)
                .text(text)
                .build();
    }
}
