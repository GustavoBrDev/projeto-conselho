package REPOSITORIES.EDUCATIONAL;

import MODELS.ENTITY.EDUCATIONAL.TeacherPreCouncil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeacherPreCouncilRepository extends JpaRepository<TeacherPreCouncil, Long>, JpaSpecificationExecutor<TeacherPreCouncil> {
}
