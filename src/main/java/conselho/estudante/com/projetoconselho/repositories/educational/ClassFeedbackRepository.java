package conselho.estudante.com.projetoconselho.repositories.educational;

import conselho.estudante.com.projetoconselho.models.entity.educational.ClassFeedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para a entidade {@link ClassFeedback}.
 * Contém métodos de acesso ao banco de dados para feedbacks de turma.
 * @author Camilly Chelest
 * @since 20/03/2025
 */
public interface ClassFeedbackRepository extends JpaRepository<ClassFeedback, Long> {

    /**
     * Busca todos os feedbacks associados a um determinado conselho.
     * @param councilId ID do conselho
     * @param pageable Configuração de paginação
     * @return Página contendo feedbacks do conselho
     */
    Page<ClassFeedback> findByCouncilId(Long councilId, Pageable pageable);
}
