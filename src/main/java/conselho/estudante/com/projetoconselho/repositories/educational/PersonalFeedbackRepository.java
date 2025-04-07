package conselho.estudante.com.projetoconselho.repositories.educational;

import conselho.estudante.com.projetoconselho.models.entity.educational.PersonalFeedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para a entidade {@link PersonalFeedback}.
 * Contém métodos de acesso ao banco de dados para feedbacks pessoais.
 * @author Camilly Chelest
 * @since 20/03/2025
 */
public interface PersonalFeedbackRepository extends JpaRepository<PersonalFeedback, Long> {

    /**
     * Busca todos os feedbacks associados a um determinado conselho.
     * @param councilId ID do conselho
     * @param pageable Configuração de paginação
     * @return Página contendo feedbacks do conselho
     */
    Page<PersonalFeedback> findByCouncilId(Long councilId, Pageable pageable);
}
