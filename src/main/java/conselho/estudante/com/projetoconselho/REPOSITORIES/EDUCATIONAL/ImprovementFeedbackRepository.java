package conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.AdvisorFeeback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImprovementFeedbackRepository extends JpaRepository<AdvisorFeeback, Long> {
}
