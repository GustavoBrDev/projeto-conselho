package MODELS.ENTITY.USERS;

import MODELS.ENTITY.ADMINISTRATION.Course;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Classe model da entidade Supervisor
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see Staff, User
 */
@EqualsAndHashCode(callSuper = true) //Pedir isso ao professor
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Supervisor extends Staff {

    @OneToMany
    private List<Course> courses;

    /**
     * Metodo para adicionar um curso ao supervisor
     * @param course o curso a ser adicionado
     * @return um booleano indicando se o curso foi adicionado. Se verdadeiro, o curso foi adicionado ao supervisor. Se falso, o curso nao foi adicionado ao supervisor
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
     * Método para remover um curso ao supervisor
     * @param course o curso a ser removido
     * @return um booleano indicando se o curso foi removido. Se verdadeiro, o curso foi removido ao supervisor. Se falso, o curso nao foi removido ao supervisor
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
}
