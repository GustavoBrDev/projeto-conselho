package MODELS.ENTITY.EDUCATIONAL;

import MODELS.ENTITY.ADMINISTRATION.Classe;
import MODELS.ENTITY.USERS.Staff;
import MODELS.ENTITY.USERS.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Classe model da entidade Council (abstração para council)
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see PreCouncil, ViewedStudents
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
    private List<Teacher> staff;
    //private List<Staff> staff;

    @OneToMany
    private List<TeacherPreCouncil> preCouncils;
    //private List<PreCouncil> preCouncils;

    private Boolean representativePreCouncilFinished;

    private Boolean teacherPreCouncilFinished;

    private Boolean councilFinished;

    private Boolean feedbackDelivered;

    private Boolean representativePreCouncilStarted;

    private Boolean teacherPreCouncilStarted;

}
