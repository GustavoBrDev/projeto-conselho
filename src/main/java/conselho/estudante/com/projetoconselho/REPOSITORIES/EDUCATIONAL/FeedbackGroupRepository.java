package conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.FeedbackGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para a entidade {@link FeedbackGroup}
 * Fornece métodos para acessar e manipular os dados de feedbacks em grupo no banco de dados.
 * @author Camilly Chelest
 * @since 19/03/2025
 */
public interface FeedbackGroupRepository extends JpaRepository<FeedbackGroup, Long> {

    /**
     * Lista todos os grupos de feedbacks por conselho com suporte a paginação.
     * @param councilId ID do conselho associado aos feedbacks
     * @param pageable Configuração de paginação
     * @return Página contendo os grupos de feedbacks do conselho
     */
    Page<FeedbackGroup> findByClassFeedback_CouncilId(Long councilId, Pageable pageable);
}
