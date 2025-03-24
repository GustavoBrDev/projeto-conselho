<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/ENTITY/EDUCATIONAL/RepresentativePreCouncil.java
package conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL;


import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
========
package conselho.estudante.com.projetoconselho.ENTITY.EDUCATIONAL;


import conselho.estudante.com.projetoconselho.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.ENTITY.USERS.Teacher;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/ENTITY/EDUCATIONAL/RepresentativePreCouncil.java
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
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