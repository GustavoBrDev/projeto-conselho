package conselho.estudante.com.projetoconselho.repositories.logs;

import conselho.estudante.com.projetoconselho.models.entity.administration.Course;
import conselho.estudante.com.projetoconselho.models.entity.logs.CourseLogs;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseLogsRepository extends MongoRepository<CourseLogs, String> {
    Page<CourseLogs> findByActor(User actor, Pageable pageable);

    Page<CourseLogs> findByTarget(Course target, Pageable pageable);

    Page<CourseLogs> findByType(String type, Pageable pageable);
}
