package REPOSITORIES.ADMINISTRATION;

import MODELS.ENTITY.ADMINISTRATION.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
