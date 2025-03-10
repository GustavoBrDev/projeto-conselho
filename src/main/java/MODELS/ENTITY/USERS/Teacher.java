package MODELS.ENTITY.USERS;

import MODELS.ENTITY.ADMINISTRATION.Course;
import MODELS.ENTITY.ADMINISTRATION.Shift;
import MODELS.ENTITY.ADMINISTRATION.Subject;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Classe model da entidade Professor
 * @author Gustavo Stinghen
 * @since 10/03/2025
 */

@EqualsAndHashCode (callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Teacher extends Staff {

    @ManyToMany
    private List<Course> courses;

    @ManyToMany
    private List<Subject> subjects;

    @ManyToMany
    private List<Shift> shifts;

    /**
     * Método para adicionar um curso ao professor
     * @param course o curso a ser adicionado em formato de {@link Course}
     * @return um booleano indicando se o curso foi adicionado. Se verdadeiro, o curso foi adicionado ao professor. Se falso, o curso nao foi adicionado ao professor
     * O curso nao pode ser adicionado se ele ja estiver na lista de cursos
     * @see Course
     */
    public boolean addCourse(Course course) {

        if (this.courses.contains(course)) {
            return false;
        } else {
            this.courses.add(course);
            return true;
        }

    }

    /**
     * Método para remover um curso ao professor
     * @param course o curso a ser removido em formato de {@link Course}
     * @return um booleano indicando se o curso foi removido. Se verdadeiro, o curso foi removido ao professor. Se falso, o curso nao foi removido ao professor
     * O curso nao pode ser removido se ele nao estiver na lista de cursos
     * @see Course
     */
    public boolean removeCourse(Course course) {

        if (this.courses.contains(course)) {
            this.courses.remove(course);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Método para adicionar uma materia ao professor
     * @param subject a materia a ser adicionada em formato de {@link Subject}
     * @return um booleano indicando se a materia foi adicionada. Se verdadeiro, a materia foi adicionada ao professor. Se falso, a materia nao foi adicionada ao professor
     * A materia nao pode ser adicionada se ela ja estiver na lista de materias
     * @see Subject
     */
    public boolean addSubject(Subject subject) {

        if (this.subjects.contains(subject)) {
            return false;
        } else {
            this.subjects.add(subject);
            return true;
        }
    }

    /**
     * Método para remover uma materia ao professor
     * @param subject a materia a ser removida em formato de {@link Subject}
     * @return um booleano indicando se a materia foi removida. Se verdadeiro, a materia foi removida ao professor. Se falso, a materia nao foi removida ao professor
     * A materia nao pode ser removida se ela nao estiver na lista de materias
     * @see Subject
     */
    public boolean removeSubject(Subject subject) {

        if (this.subjects.contains(subject)) {
            this.subjects.remove(subject);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Método para adicionar um turno ao professor
     * @param shift o turno a ser adicionado em formato de {@link Shift}
     * @return um booleano indicando se o turno foi adicionado. Se verdadeiro, o turno foi adicionado ao professor. Se falso, o turno nao foi adicionado ao professor
     * O turno nao pode ser adicionado se ele ja estiver na lista de turnos
     * @see Shift
     */
    public boolean addShift(Shift shift) {

        if (this.shifts.contains(shift)) {
            return false;
        } else {
            this.shifts.add(shift);
            return true;
        }

    }

    /**
     * Método para remover um turno ao professor
     * @param shift o turno a ser removido em formato de {@link Shift}
     * @return um booleano indicando se o turno foi removido. Se verdadeiro, o turno foi removido ao professor. Se falso, o turno nao foi removido ao professor
     * O turno nao pode ser removido se ele nao estiver na lista de turnos
     * @see Shift
     */
    public boolean removeShift(Shift shift) {

        if (this.shifts.contains(shift)) {
            this.shifts.remove(shift);
            return true;
        } else {
            return false;
        }

    }
}
