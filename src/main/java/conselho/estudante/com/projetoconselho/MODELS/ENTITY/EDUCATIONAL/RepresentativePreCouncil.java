package conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.List;


/**
 * Classe concreta da entidade RepresentativePreCouncil (Pre-council de representantes)
 * @author Gustavo Stinghen
 * @since 13/03/2025
 * @see Council, TeacherFeeback, AdvisorFeeback, SupervisorFeeback
 *
 * Atualizado em 26/03/2025
 * Alterado para AvaliableTeacher
 * @author Gustavo Stinghen
 * @see AvaliableTeacher
 */


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class RepresentativePreCouncil implements PreCouncil {


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
    private Classe classe;


    @Column(nullable = false)
    private Boolean isFilled;

    @ManyToMany
    private List<AvaliableTeacher> teachers;

    @ManyToOne
    private AdvisorFeeback advisorFeeback;


    @ManyToOne
    private SupervisorFeedback supervisorFeeback;


    @OneToMany(fetch = FetchType.LAZY)
    private List<TeacherFeeback> teacherFeebacks;


    @OneToMany(fetch = FetchType.LAZY)
    private List<ItemFeedback> itemFeedbacks;


}
