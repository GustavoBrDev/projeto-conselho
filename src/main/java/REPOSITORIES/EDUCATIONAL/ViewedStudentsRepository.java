package REPOSITORIES.EDUCATIONAL;

import MODELS.ENTITY.EDUCATIONAL.ViewedStudents;
import MODELS.ENTITY.EDUCATIONAL.Council;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewedStudentsRepository extends JpaRepository<ViewedStudents, Long> {

        ViewedStudents findByCouncil(Council council);
}
