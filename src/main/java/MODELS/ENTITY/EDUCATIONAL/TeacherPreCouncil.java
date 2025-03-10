package MODELS.ENTITY.EDUCATIONAL;

import MODELS.ENTITY.ADMINISTRATION.Subject;
import MODELS.ENTITY.USERS.Student;
import MODELS.ENTITY.USERS.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Classe concreta da entidade TeacherPreCouncil (Pre-council de professores)
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see Council, PersonalFeedback, PreCouncil
 */

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TeacherPreCouncil extends PreCouncil {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Subject subject;

    @ManyToMany
    private List<Student> students;

    @OneToMany
    private List<PersonalFeedback> feedbacks;

    /**
     * Método para adicionar um feedback ao pre-council
     * @param feedback feedback a ser adicionado em formato de {@link PersonalFeedback}
     * @return um booleano indicando se o feedback foi adicionado. Se verdadeiro, o feedback foi adicionado ao pre-council. Se falso, o feedback nao foi adicionado ao pre-council
     * O feedback nao pode ser adicionado se ele ja estiver na lista de feedbacks
     * @see PersonalFeedback
     */
    public boolean addFeedback(PersonalFeedback feedback) {

        if ( feedbacks.contains(feedback) ) {
            return false;
        } else {
            feedbacks.add(feedback);
            return true;
        }
    }

    /**
     * Método para remover um feedback ao pre-council
     * @param feedback feedback a ser removido em formato de {@link PersonalFeedback}
     * @return um booleano indicando se o feedback foi removido. Se verdadeiro, o feedback foi removido ao pre-council. Se falso, o feedback nao foi removido ao pre-council
     * O feedback nao pode ser removido se ele nao estiver na lista de feedbacks
     * @see PersonalFeedback
     */
    public boolean removeFeedback(PersonalFeedback feedback) {

        if ( feedbacks.contains(feedback) ) {
            feedbacks.remove(feedback);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método para adicionar um aluno ao pre-council
     * @param student aluno a ser adicionado em formato de {@link Student}
     * @return um booleano indicando se o aluno foi adicionado. Se verdadeiro, o aluno foi adicionado ao pre-council. Se falso, o aluno nao foi adicionado ao pre-council
     * O aluno nao pode ser adicionado se ele ja estiver na lista de alunos
     * @see Student
     */
    public boolean addStudent(Student student) {

        if ( students.contains(student) ) {
            return false;
        } else {
            students.add(student);
            return true;
        }
    }

    /**
     * Método para remover um aluno ao pre-council
     * @param student aluno a ser removido em formato de {@link Student}
     * @return um booleano indicando se o aluno foi removido. Se verdadeiro, o aluno foi removido ao pre-council. Se falso, o aluno nao foi removido ao pre-council
     * O aluno nao pode ser removido se ele nao estiver na lista de alunos
     * @see Student
     */
    public boolean removeStudent(Student student) {

        if ( students.contains(student) ) {
            students.remove(student);
            return true;
        } else {
            return false;
        }
    }

}
