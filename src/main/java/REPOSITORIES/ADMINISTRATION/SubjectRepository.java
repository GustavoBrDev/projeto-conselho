package REPOSITORIES.ADMINISTRATION;

import MODELS.ENTITY.ADMINISTRATION.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
