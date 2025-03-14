package MODELS.ENTITY.EDUCATIONAL;


import MODELS.ENTITY.ADMINISTRATION.Classe;
import MODELS.ENTITY.USERS.Advisor;
import MODELS.ENTITY.USERS.RegularUser;
import MODELS.ENTITY.USERS.Supervisor;
import MODELS.ENTITY.USERS.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Classe concreta da entidade RepresentativePreCouncil (Pre-council de representantes)
 * @author Gustavo Stinghen
 * @since 13/03/2025
 * @see Council, TeacherFeeback, AdvisorFeeback, SupervisorFeeback
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class RepresentativePreCouncil {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Council council;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Classe classe;

    @Column(nullable = false)
    private Boolean isFilled;

    @ManyToMany
    private List<Teacher> teachers;

    @ManyToOne
    private AdvisorFeeback advisorFeeback;

    @ManyToOne
    private SupervisorFeedback supervisorFeeback;

    @OneToMany
    private List<TeacherFeeback> teacherFeebacks;

    @OneToMany
    private List<ItemFeedback> itemFeedbacks;

}
