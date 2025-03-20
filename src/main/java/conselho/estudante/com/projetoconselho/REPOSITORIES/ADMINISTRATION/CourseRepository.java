package conselho.estudante.com.projetoconselho.REPOSITORIES.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.ENTITY.ADMINISTRATION.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
