package conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.TeacherFeeback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para a entidade {@link TeacherFeeback}.
 * Contém métodos de acesso ao banco de dados para feedbacks de professores (Teacher).
 * @author Camilly Chelest
 * @since 19/03/2025
 */
public interface TeacherFeedbackRepository extends JpaRepository<TeacherFeeback, Long> {

    /**
     * Busca todos os feedbacks associados a um determinado conselho.
     * @param councilId ID do conselho
     * @param pageable Configuração de paginação
     * @return Página contendo feedbacks do conselho
     */
    Page<TeacherFeeback> findByCouncilId(Long councilId, Pageable pageable);
}
