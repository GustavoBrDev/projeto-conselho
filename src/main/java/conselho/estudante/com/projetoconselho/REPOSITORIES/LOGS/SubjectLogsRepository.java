package conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Subject;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.SubjectLogs;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubjectLogsRepository extends MongoRepository<SubjectLogs, String> {
    Page<SubjectLogs> findByActor(User actor, Pageable pageable);

    Page<SubjectLogs> findByTarget(Subject target, Pageable pageable);

    Page<SubjectLogs> findByType(String type, Pageable pageable);
}
