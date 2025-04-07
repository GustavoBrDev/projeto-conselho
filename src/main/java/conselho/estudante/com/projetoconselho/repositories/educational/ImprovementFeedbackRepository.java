package conselho.estudante.com.projetoconselho.repositories.educational;

import conselho.estudante.com.projetoconselho.models.entity.educational.AdvisorFeeback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImprovementFeedbackRepository extends JpaRepository<AdvisorFeeback, Long> {
}
