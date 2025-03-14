package REPOSITORIES.EDUCATIONAL;

import MODELS.ENTITY.EDUCATIONAL.AdvisorFeeback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImprovementFeedbackRepository extends JpaRepository<AdvisorFeeback, Long> {
}
