package conselho.estudante.com.projetoconselho.repositories.logs;

import conselho.estudante.com.projetoconselho.models.entity.administration.Subject;
import conselho.estudante.com.projetoconselho.models.entity.logs.SubjectLogs;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubjectLogsRepository extends MongoRepository<SubjectLogs, String> {
    Page<SubjectLogs> findByActor(User actor, Pageable pageable);

    Page<SubjectLogs> findByTarget(Subject target, Pageable pageable);

    Page<SubjectLogs> findByType(String type, Pageable pageable);
}
