package conselho.estudante.com.projetoconselho.repositories.educational;

import conselho.estudante.com.projetoconselho.models.entity.educational.SupervisorFeedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para a entidade {@link SupervisorFeedback}.
 * Contém métodos de acesso ao banco de dados para feedbacks de supervisores.
 * @author Camilly Chelest
 * @since 19/03/2025
 */
public interface SupervisorFeedbackRepository extends JpaRepository<SupervisorFeedback, Long> {

    /**
     * Busca todos os feedbacks associados a um determinado conselho.
     * @param councilId ID do conselho
     * @param pageable Configuração de paginação
     * @return Página contendo feedbacks do conselho
     */
    Page<SupervisorFeedback> findByCouncilId(Long councilId, Pageable pageable);
}
