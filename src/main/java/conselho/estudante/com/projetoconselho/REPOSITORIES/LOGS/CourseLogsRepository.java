package conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Course;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.CourseLogs;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseLogsRepository extends MongoRepository<CourseLogs, String> {
    Page<CourseLogs> findByActor(User actor, Pageable pageable);

    Page<CourseLogs> findByTarget(Course target, Pageable pageable);

    Page<CourseLogs> findByType(String type, Pageable pageable);
}
