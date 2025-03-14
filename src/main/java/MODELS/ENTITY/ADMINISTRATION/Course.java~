package MODELS.ENTITY.ADMINISTRATION;

import MODELS.ENTITY.USERS.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Classe model da entidade Curso
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see Classe
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String visualIdentity;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Integer workLoad;

    @Column(nullable = false)
    private String level;

    @ManyToMany
    private List<Subject> subjects;

    @ManyToMany (mappedBy = "courses")
    private List<Teacher> teachers;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Shift shift;

    @OneToMany (mappedBy = "course")
    @JoinColumn(nullable = false)
    private List<Classe> classes;

    /**
     * Metodo para adicionar um professor ao curso
     * @param teacher o professor a ser adicionado em formato de {@link Teacher}
     * @return um booleano indicando se o professor foi adicionado. Se verdadeiro, o professor foi adicionado ao curso. Se falso, o professor nao foi adicionado ao curso
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
     * Metodo para remover um professor ao curso
     * @param teacher o professor a ser removido em formato de {@link Teacher}
     * @return um booleano indicando se o professor foi removido. Se verdadeiro, o professor foi removido ao curso. Se falso, o professor nao foi removido ao curso
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
     * Metodo para adicionar uma materia ao curso
     * @param subject a materia a ser adicionada em formato de {@link Subject}
     * @return um booleano indicando se a materia foi adicionada. Se verdadeiro, a materia foi adicionada ao curso. Se falso, a materia nao foi adicionada ao curso
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
     * Metodo para remover uma materia ao curso
     * @param subject a materia a ser removida em formato de {@link Subject}
     * @return um booleano indicando se a materia foi removida. Se verdadeiro, a materia foi removida ao curso. Se falso, a materia nao foi removida ao curso
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
     * Metodo para adicionar uma classe ao curso
     * @param classe a classe a ser adicionada em formato de {@link Classe}
     * @return um booleano indicando se a classe foi adicionada. Se verdadeiro, a classe foi adicionada ao curso. Se falso, a classe nao foi adicionada ao curso
     * A classe nao pode ser adicionada se ela ja estiver na lista de classes
     * @see Classe
     */
    public boolean addClasse(Classe classe) {

        if (this.classes.contains(classe)) {
            return false;
        } else {
            this.classes.add(classe);
            return true;
        }
    }

    /**
     * Metodo para remover uma classe ao curso
     * @param classe a classe a ser removida em formato de {@link Classe}
     * @return um booleano indicando se a classe foi removida. Se verdadeiro, a classe foi removida ao curso. Se falso, a classe nao foi removida ao curso
     * A classe nao pode ser removida se ela nao estiver na lista de classes
     * @see Classe
     */
    public boolean removeClasse(Classe classe) {

        if (this.classes.contains(classe)) {
            this.classes.remove(classe);
            return true;
        } else {
            return false;
        }
    }
}
