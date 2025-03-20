package conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS;

import conselho.estudante.com.projetoconselho.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.ENTITY.LOGS.CourseLogs;
import conselho.estudante.com.projetoconselho.ENTITY.USERS.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseLogsRepository extends MongoRepository<CourseLogs, String> {
    Page<CourseLogs> findByActor(User actor, Pageable pageable);

    Page<CourseLogs> findByTarget(Classe target, Pageable pageable);

    Page<CourseLogs> findByType(String type, Pageable pageable);
}
