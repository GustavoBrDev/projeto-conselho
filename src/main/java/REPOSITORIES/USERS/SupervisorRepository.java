package REPOSITORIES.USERS;

import MODELS.ENTITY.USERS.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {
}
