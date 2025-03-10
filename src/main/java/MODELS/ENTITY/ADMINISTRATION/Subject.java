package MODELS.ENTITY.ADMINISTRATION;

import MODELS.ENTITY.USERS.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Classe model da entidade Disciplina
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see Teacher
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Subject {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Integer workLoad;

    @ManyToMany ( mappedBy = "subjects" )
    private List<Teacher> teachers;

    /**
     * Metodo para adicionar um professor a disciplina
     * @param teacher o professor a ser adicionado em formato de {@link Teacher}
     * @return um booleano indicando se o professor foi adicionado. Se verdadeiro, o professor foi adicionado a disciplina. Se falso, o professor nao foi adicionado a disciplina
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
     * Metodo para remover um professor da disciplina
     * @param teacher o professor a ser removido em formato de {@link Teacher}
     * @return um booleano indicando se o professor foi removido. Se verdadeiro, o professor foi removido da disciplina. Se falso, o professor nao foi removido da disciplina
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
}
