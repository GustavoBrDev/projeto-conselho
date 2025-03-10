package REPOSITORIES.USERS;

import MODELS.ENTITY.USERS.Technique;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechniqueRepository extends JpaRepository<Technique, Long> {
}
