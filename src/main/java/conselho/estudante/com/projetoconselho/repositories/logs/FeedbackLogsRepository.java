package conselho.estudante.com.projetoconselho.repositories.logs;

import conselho.estudante.com.projetoconselho.models.entity.educational.Feedback;
import conselho.estudante.com.projetoconselho.models.entity.logs.FeedbackLogs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedbackLogsRepository extends MongoRepository<FeedbackLogs, String> {

    Page<FeedbackLogs> findByActor(Object actor, Pageable pageable);

    Page<FeedbackLogs> findByTarget(Feedback target, Pageable pageable);

    Page<FeedbackLogs> findByType(String type, Pageable pageable);
}
