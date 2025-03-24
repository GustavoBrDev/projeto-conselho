<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/ENTITY/EDUCATIONAL/CallToChatStudents.java
package conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
========
package conselho.estudante.com.projetoconselho.ENTITY.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.ENTITY.USERS.Student;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/ENTITY/EDUCATIONAL/CallToChatStudents.java
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Classe model da entidade CallToChatStudents
 * @author Gustavo Stinghen
 * @since 14/03/2025
 * @see Council
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class CallToChatStudents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Council council;

    @OneToMany
    private List<Student> students;

    /**
     * Metodo para adicionar um aluno ao pre-council
     * @param student aluno a ser adicionado em formato de {@link Student}
     * @return um booleano indicando se o aluno foi adicionado. Se verdadeiro, o aluno foi adicionado ao a lista. Se falso, o aluno nao foi adicionado a lista
     * O aluno nao pode ser adicionado se ele ja estiver na lista de alunos
     * @see Student
     */
    public boolean addStudent(Student student){
        if ( students.contains(student) ) {
            return false;
        } else {
            students.add(student);
            return true;
        }
    }

    /**
     * Metodo para remover um aluno ao pre-council
     * @param student aluno a ser removido em formato de {@link Student}
     * @return um booleano indicando se o aluno foi removido. Se verdadeiro, o aluno foi removido da lista. Se falso, o aluno nao foi removido da lista
     * O aluno nao pode ser removido se ele nao estiver na lista de alunos
     * @see Student
     */
    public boolean removeStudent(Student student){
        if ( students.contains(student) ) {
            students.remove(student);
            return true;
        } else {
            return false;
        }
    }
}
