package conselho.estudante.com.projetoconselho.models.entity.educational;

import conselho.estudante.com.projetoconselho.models.entity.administration.Subject;
import conselho.estudante.com.projetoconselho.models.entity.users.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Classe model da entidade AvaliableTeacher
 * Responsável por associar um professor a uma disciplina no momento da avaliação
 * @author Gustavo Stinghen
 * @since 26/03/2025
 * @see Teacher
 * @see Subject
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class AvaliableTeacher {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Teacher teacher;

    @ManyToMany
    private List<Subject> subjects;
}
