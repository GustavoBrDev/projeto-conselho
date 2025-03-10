package REPOSITORIES.USERS;

import MODELS.ENTITY.USERS.Advisor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvisorRepository extends JpaRepository<Advisor, Long> {
}
