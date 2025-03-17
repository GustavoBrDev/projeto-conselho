package MODELS.ENTITY.EDUCATIONAL;

import MODELS.ENTITY.ADMINISTRATION.Classe;
import MODELS.ENTITY.USERS.Advisor;
import MODELS.ENTITY.USERS.Staff;
import MODELS.ENTITY.USERS.Teacher;
import MODELS.ENTITY.USERS.Technique;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Classe model da entidade Council
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see ViewedStudents
 *
 * Atualizado em 13/03/2025
 * @author Gustavo Stinghen
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Council {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Classe classe;

    @Column(nullable = false)
    private Date createdAt;

    @ManyToMany
    @JoinColumn(nullable = false)
    private List<Teacher> teachers;

    @ManyToOne
    private Advisor advisor;

    @ManyToMany
    private List<Technique> techniques;

    @OneToMany
    private List<TeacherPreCouncil> teacherPreCouncils;

    @OneToOne
    private ViewedStudents viewedStudents;

    @OneToOne
    private RepresentativePreCouncil representativePreCouncil;

    private Boolean representativePreCouncilFinished;

    private Boolean teacherPreCouncilFinished;

    private Boolean councilFinished;

    private Boolean feedbackDelivered;

    private Boolean representativePreCouncilStarted;

    private Boolean teacherPreCouncilStarted;

    /**
     * Método para adicionar um professor ao council
     * @param teacher o professor a ser adicionado em formato de {@link Teacher}
     * @return um booleano indicando se o professor foi adicionado. Se verdadeiro, o professor foi adicionado ao council. Se falso, o professor nao foi adicionado ao council
     * O professor nao pode ser adicionado se ele ja estiver na lista de professores
     * @see Teacher
     * @since 13/03/2025
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
     * Método para remover um professor ao council
     * @param teacher o professor a ser removido em formato de {@link Teacher}
     * @return um booleano indicando se o professor foi removido. Se verdadeiro, o professor foi removido ao council. Se falso, o professor nao foi removido ao council
     * O professor nao pode ser removido se ele nao estiver na lista de professores
     * @see Teacher
     * @since 13/03/2025
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
     * Método para adicionar um tecnico ao council
     * @param technique o tecnico a ser adicionado em formato de {@link Technique}
     * @return um booleano indicando se o tecnico foi adicionado. Se verdadeiro, o tecnico foi adicionado ao council. Se falso, o tecnico nao foi adicionado ao council
     * O tecnico nao pode ser adicionado se ele ja estiver na lista de tecnicos
     * @see Technique
     * @since 13/03/2025
     */
    public boolean addTechnique(Technique technique) {

        if (this.techniques.contains(technique)) {
            return false;
        } else {
            this.techniques.add(technique);
            return true;
        }
    }

    /**
     * Metodo para remover um tecnico ao council
     * @param technique o tecnico a ser removido em formato de {@link Technique}
     * @return um booleano indicando se o tecnico foi removido. Se verdadeiro, o tecnico foi removido ao council. Se falso, o tecnico nao foi removido ao council
     * O tecnico nao pode ser removido se ele nao estiver na lista de tecnicos
     * @see Technique
     * @since 13/03/2025
     */
    public boolean removeTechnique(Technique technique) {

        if (this.techniques.contains(technique)) {
            this.techniques.remove(technique);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo para adicionar um pre-council ao council
     * @param teacherPreCouncil o pre-council a ser adicionado em formato de {@link TeacherPreCouncil}
     * @return um booleano indicando se o pre-council foi adicionado. Se verdadeiro, o pre-council foi adicionado ao council. Se falso, o pre-council nao foi adicionado ao council
     * O pre-council nao pode ser adicionado se ele ja estiver na lista de pre-councils
     * @see TeacherPreCouncil
     * @since 13/03/2025
     */
    public boolean addTeacherPreCouncil(TeacherPreCouncil teacherPreCouncil) {

        if (this.teacherPreCouncils.contains(teacherPreCouncil)) {
            return false;
        } else {
            this.teacherPreCouncils.add(teacherPreCouncil);
            return true;
        }
    }

    /**
     * Metodo para remover um pre-council ao council
     * @param teacherPreCouncil o pre-council a ser removido em formato de {@link TeacherPreCouncil}
     * @return um booleano indicando se o pre-council foi removido. Se verdadeiro, o pre-council foi removido ao council. Se falso, o pre-council nao foi removido ao council
     * O pre-council nao pode ser removido se ele nao estiver na lista de pre-councils
     * @see TeacherPreCouncil
     * @since 13/03/2025
     */
    public boolean removeTeacherPreCouncil(TeacherPreCouncil teacherPreCouncil) {

        if (this.teacherPreCouncils.contains(teacherPreCouncil)) {
            this.teacherPreCouncils.remove(teacherPreCouncil);
            return true;
        } else {
            return false;
        }
    }

}
