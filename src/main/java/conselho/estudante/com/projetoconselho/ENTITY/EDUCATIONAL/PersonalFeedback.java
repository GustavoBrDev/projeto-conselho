<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/ENTITY/EDUCATIONAL/PersonalFeedback.java
package conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
========
package conselho.estudante.com.projetoconselho.ENTITY.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.ENTITY.USERS.Student;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/ENTITY/EDUCATIONAL/PersonalFeedback.java
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
}