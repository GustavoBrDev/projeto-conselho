package conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.FeedbackGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositório para a entidade {@link FeedbackGroup}.
 * Contém métodos de acesso ao banco de dados para grupos de feedbacks.
 * @author Camilly Chelest
 * @since 20/03/2025
 */

public interface FeedbackGroupRepository extends JpaRepository<FeedbackGroup, Long> {
    Page<FeedbackGroup> findByPersonalFeedback_Student_Id(Long studentId, Pageable pageable);
    Page<FeedbackGroup> findByClassFeedback_Classe_Id(Long classId, Pageable pageable);


    /**
     * Busca todos os grupos de feedback de um determinado conselho.
     *
     * @param councilId ID do conselho
     * @param pageable  Configuração da paginação
     * @return Página contendo os grupos de feedbacks do conselho especificado
     */
    @Query("""
            SELECT fg FROM FeedbackGroup fg
            JOIN fg.classFeedback cf
            JOIN cf.council c
            WHERE c.id = :councilId
            """)
    Page<FeedbackGroup> findByClassFeedback_CouncilId(@Param("councilId") Long councilId, Pageable pageable);

    /**
     * Pesquisa inteligente nos grupos de feedbacks, buscando dentro de atributos do PersonalFeedback.
     *
     * @param term     Termo de busca
     * @param pageable Configuração da paginação
     * @return Página contendo os grupos de feedbacks que correspondem ao termo pesquisado
     */
    @Query("""
            SELECT fg FROM FeedbackGroup fg
            JOIN fg.personalFeedback pf
            JOIN pf.student s
            JOIN s.classe c
            JOIN c.course course
            WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :term, '%'))
               OR LOWER(c.name) LIKE LOWER(CONCAT('%', :term, '%'))
               OR LOWER(course.name) LIKE LOWER(CONCAT('%', :term, '%'))
               OR CAST(fg.date AS string) LIKE CONCAT('%', :term, '%')
            """)
    Page<FeedbackGroup> searchByPersonalFeedbackAttributes(@Param("term") String term, Pageable pageable);
}

