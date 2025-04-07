package conselho.estudante.com.projetoconselho.repositories.educational;

import conselho.estudante.com.projetoconselho.models.entity.educational.AdvisorFeeback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para a entidade {@link AdvisorFeeback}.
 * Contém métodos de acesso ao banco de dados para feedbacks de orientadores (Advisor).
 * @author Camilly Chelest
 * @since 19/03/2025
 */
public interface AdvisorFeedbackRepository extends JpaRepository<AdvisorFeeback, Long> {

    /**
     * Busca todos os feedbacks associados a um determinado conselho.
     * @param councilId ID do conselho
     * @param pageable Configuração de paginação
     * @return Página contendo feedbacks do conselho
     */
    Page<AdvisorFeeback> findByCouncilId(Long councilId, Pageable pageable);
}
