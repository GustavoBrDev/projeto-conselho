package conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ADMINISTRATION.SubjectResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.TeacherPreCouncilResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.TeacherResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Subject;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
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
    private Council council;

    @ManyToOne
    private Classe classe;

    @Column(nullable = false)
    private Boolean isFilled;

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
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
     * @see TeacherResponseDTO
     * @see SubjectResponseDTO
     */
    public TeacherPreCouncilResponseDTO toDTO() {
        return TeacherPreCouncilResponseDTO.builder()
                .id(this.id)
                .createdAt(this.createdAt)
                .startDate(this.startDate)
                .endDate(this.endDate)
                /*.council(this.council.toDTO())
                .classe(this.classe.toDTO())*/
                .isFilled(this.isFilled)
                //.teacher(this.teacher.toDTO())
                //.subject(this.subject.toDTO())
                .build();
    }
}
