<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/ENTITY/EDUCATIONAL/TeacherFeeback.java
package conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
========
package conselho.estudante.com.projetoconselho.ENTITY.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.ENTITY.USERS.Teacher;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/ENTITY/EDUCATIONAL/TeacherFeeback.java
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
