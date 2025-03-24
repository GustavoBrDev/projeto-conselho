<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/ENTITY/EDUCATIONAL/TeacherPreCouncil.java
package conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL;

import MODELS.DTO.RESPONSE.*;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Subject;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.*;
========
package conselho.estudante.com.projetoconselho.ENTITY.EDUCATIONAL;

import MODELS.DTO.RESPONSE.*;
import conselho.estudante.com.projetoconselho.DTO.RESPONSE.*;
import conselho.estudante.com.projetoconselho.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.ENTITY.ADMINISTRATION.Subject;
import conselho.estudante.com.projetoconselho.ENTITY.USERS.Teacher;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/ENTITY/EDUCATIONAL/TeacherPreCouncil.java
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * Classe concreta da entidade TeacherPreCouncil (Pre-council de professores)
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see Council, PersonalFeedback, PreCouncil
 *
 * Atualizado em 13/03/2025
 * Removido lista de estudantes
 * @author Gustavo Stinghen
 *
 * Atualizado em 17/03/2025
 * Utilização de uma interface
 * @author Gustavo Stinghen
 * @see PreCouncil
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TeacherPreCouncil implements PreCouncil {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Council council;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Classe classe;

    @Column(nullable = false)
    private Boolean isFilled;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Subject subject;

    @OneToMany
    private List<PersonalFeedback> feedbacks;

    /**
     * Método para adicionar um feedback ao pre-council
     * @param feedback feedback a ser adicionado em formato de {@link PersonalFeedback}
     * @return um booleano indicando se o feedback foi adicionado. Se verdadeiro, o feedback foi adicionado ao pre-council. Se falso, o feedback nao foi adicionado ao pre-council
     * O feedback nao pode ser adicionado se ele ja estiver na lista de feedbacks
     * @see PersonalFeedback
     */
    public boolean addFeedback(PersonalFeedback feedback) {

        if ( feedbacks.contains(feedback) ) {
            return false;
        } else {
            feedbacks.add(feedback);
            return true;
        }
    }

    /**
     * Método para remover um feedback ao pre-council
     * @param feedback feedback a ser removido em formato de {@link PersonalFeedback}
     * @return um booleano indicando se o feedback foi removido. Se verdadeiro, o feedback foi removido ao pre-council. Se falso, o feedback nao foi removido ao pre-council
     * O feedback nao pode ser removido se ele nao estiver na lista de feedbacks
     * @see PersonalFeedback
     */
    public boolean removeFeedback(PersonalFeedback feedback) {

        if ( feedbacks.contains(feedback) ) {
            feedbacks.remove(feedback);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Converte a entidade {@link TeacherPreCouncil} para um objeto DTO {@link TeacherPreCouncilResponseDTO}.
     * Este método mapeia os dados da entidade para a estrutura de resposta, incluindo informações de conselho,
     * classe, professor e disciplina.
     *
     * @return Um objeto {@link TeacherPreCouncilResponseDTO} contendo os dados mapeados da entidade.
     *         Inclui o ID, datas de criação, início e término, informações do conselho, classe, status de preenchimento,
     *         professor e disciplina associados.
     *
     * @see CouncilResponseDTO
     * @see ClasseResponseDTO
     * @see TeacherResponseDTO
     * @see SubjectResponseDTO
     */
    public TeacherPreCouncilResponseDTO toDTO() {
        return new TeacherPreCouncilResponseDTO(
                this.id,
                this.createdAt,
                this.startDate,
                this.endDate,
                new CouncilResponseDTO(this.council.getId(), this.council.getName()), // AJUSTAR QUANDO AS DTOS FOREM FEITAS
                new ClasseResponseDTO(this.classe.getId(), this.classe.getName()), // AJUSTAR QUANDO AS DTOS FOREM FEITAS
                this.isFilled,
                new TeacherResponseDTO(this.teacher.getId(), this.teacher.getName()), // AJUSTAR QUANDO AS DTOS FOREM FEITAS
                new SubjectResponseDTO(this.subject.getId(), this.subject.getName(), this.subject.getWorkLoad())
        );
    }


}
