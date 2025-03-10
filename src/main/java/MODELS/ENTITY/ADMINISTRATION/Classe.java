package MODELS.ENTITY.ADMINISTRATION;

import MODELS.ENTITY.USERS.Representative;
import MODELS.ENTITY.USERS.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Classe model da entidade Classe
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see Student
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Classe {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String acronym;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Course course;

    @ManyToMany
    private List<Student> students;

    @OneToOne
    @JoinColumn(nullable = false)
    private Representative representative;

    @Column(nullable = false)
    private boolean active;

    /**
     * Método para adicionar um aluno a classe
     * @param student aluno a ser adicionado em formato de {@link Student}
     * @return um booleano indicando se o aluno foi adicionado. Se verdadeiro, o aluno foi adicionado a classe. Se falso, o aluno nao foi adicionado a classe
     * O aluno nao pode ser adicionado se ele ja estiver na lista de alunos
     * @see Student
     */
    public boolean addStudent(Student student) {

        if (this.students.contains(student)) {
            return false;
        } else {
            this.students.add(student);
            return true;
        }

    }

    /**
     * Método para remover um aluno da classe
     * @param student aluno a ser removido em formato de {@link Student}
     * @return um booleano indicando se o aluno foi removido. Se verdadeiro, o aluno foi removido da classe. Se falso, o aluno nao foi removido da classe
     * O aluno nao pode ser removido se ele nao estiver na lista de alunos
     * @see Student
     */
    public boolean removeStudent(Student student) {

        if (this.students.contains(student)) {
            this.students.remove(student);
            return true;
        } else {
            return false;
        }

    }
}
