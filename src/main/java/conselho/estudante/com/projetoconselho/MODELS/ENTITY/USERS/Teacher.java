package conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS;


import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.TeacherResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Course;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Shift;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Subject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.List;


/**
 * Classe model da entidade Professor
 * @author Gustavo Stinghen
 * @since 10/03/2025
 *
 * Atualizado em 13/03/2025
 * @author Gustavo Stinghen
 *
 * Atualizado em 20/03/2025
 * @author Alex Zastrow
 */


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
public class Teacher implements User {


    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;


    private String image;


    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private String email;


    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    private Date createdAt;


    @Column(nullable = false)
    private Long register;


    @ManyToMany
    private List<Course> courses;


    @ManyToMany
    private List<Subject> subjects;


    @ManyToMany
    private List<Shift> shifts;


    /** Metodo para adicionar um curso ao professor
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
     * Metodo para remover um curso ao professor
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
     * Metodo para remover uma materia ao professor
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


    public TeacherResponseDTO toDTO() {
        return TeacherResponseDTO.builder()
                .id(this.getId())
                .name(this.getName())
                .email(this.getEmail())
                .image(this.getImage())
                .register(this.getRegister())
                .build();
    }
}
