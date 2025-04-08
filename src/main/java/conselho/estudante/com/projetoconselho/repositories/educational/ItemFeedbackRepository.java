package conselho.estudante.com.projetoconselho.repositories.educational;

import conselho.estudante.com.projetoconselho.models.entity.educational.ItemFeedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para a entidade {@link ItemFeedback}.
 * Contém métodos de acesso ao banco de dados para feedbacks de itens.
 * @author Camilly Chelest
 * @since 19/03/2025
 */
public interface ItemFeedbackRepository extends JpaRepository<ItemFeedback, Long> {

    /**
     * Busca todos os feedbacks associados a um determinado conselho.
     * @param councilId ID do conselho
     * @param pageable Configuração de paginação
     * @return Página contendo feedbacks do conselho
     */
    Page<ItemFeedback> findByCouncilId(Long councilId, Pageable pageable);
}
