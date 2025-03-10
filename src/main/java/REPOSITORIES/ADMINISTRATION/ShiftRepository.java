package REPOSITORIES.ADMINISTRATION;

import MODELS.ENTITY.ADMINISTRATION.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
}
