package conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS;

import conselho.estudante.com.projetoconselho.ENTITY.EDUCATIONAL.Feedback;
import conselho.estudante.com.projetoconselho.ENTITY.LOGS.FeedbackLogs;
import conselho.estudante.com.projetoconselho.ENTITY.USERS.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedbackLogsRepository extends MongoRepository<FeedbackLogs, String> {
    Page<FeedbackLogs> findByActor(User actor, Pageable pageable);

    Page<FeedbackLogs> findByTarget(Feedback target, Pageable pageable);

    Page<FeedbackLogs> findByType(String type, Pageable pageable);
}
