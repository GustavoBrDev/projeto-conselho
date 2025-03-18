package REPOSITORIES.EDUCATIONAL;

import MODELS.ENTITY.EDUCATIONAL.PersonalFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalFeedbackRepository extends JpaRepository<PersonalFeedback, Long> {
}
