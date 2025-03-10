package MODELS.ENTITY.ADMINISTRATION;

import MODELS.ENTITY.USERS.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Classe model da entidade Turno
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see Teacher, Course
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Shift {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date createdAt;

    @ManyToMany (mappedBy = "shifts")
    private List<Teacher> teachers;

    @OneToMany (mappedBy = "shift")
    private List<Course> course;

    /**
     * Método para adicionar um professor ao turno
     * @param teacher o professor a ser adicionado em formato de {@link Teacher}
     * @return um booleano indicando se o professor foi adicionado. Se verdadeiro, o professor foi adicionado ao turno. Se falso, o professor nao foi adicionado ao turno
     * O professor nao pode ser adicionado se ele ja estiver na lista de professores
     * @see Teacher
     */
    public boolean addTeacher(Teacher teacher) {

        if (this.teachers.contains(teacher)) {
            return false;
        } else {
            this.teachers.add(teacher);
            return true;
        }
    }

    /**
     * Método para remover um professor ao turno
     * @param teacher o professor a ser removido em formato de {@link Teacher}
     * @return um booleano indicando se o professor foi removido. Se verdadeiro, o professor foi removido ao turno. Se falso, o professor nao foi removido ao turno
     * O professor nao pode ser removido se ele nao estiver na lista de professores
     * @see Teacher
     */
    public boolean removeTeacher(Teacher teacher) {

        if (this.teachers.contains(teacher)) {
            this.teachers.remove(teacher);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método para adicionar um curso ao turno
     * @param course o curso a ser adicionado em formato de {@link Course}
     * @return um booleano indicando se o curso foi adicionado. Se verdadeiro, o curso foi adicionado ao turno. Se falso, o curso nao foi adicionado ao turno
     * O curso nao pode ser adicionado se ele ja estiver na lista de cursos
     * @see Course
     */
    public boolean addCourse(Course course) {

        if (this.course.contains(course)) {
            return false;
        } else {
            this.course.add(course);
            return true;
        }
    }

    /**
     * Método para remover um curso ao turno
     * @param course o curso a ser removido em formato de {@link Course}
     * @return um booleano indicando se o curso foi removido. Se verdadeiro, o curso foi removido ao turno. Se falso, o curso nao foi removido ao turno
     * O curso nao pode ser removido se ele nao estiver na lista de cursos
     * @see Course
     */
    public boolean removeCourse(Course course) {

        if (this.course.contains(course)) {
            this.course.remove(course);
            return true;
        } else {
            return false;
        }
    }
}
