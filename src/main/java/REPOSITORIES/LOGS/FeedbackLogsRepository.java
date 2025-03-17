package REPOSITORIES.LOGS;

import MODELS.ENTITY.EDUCATIONAL.Council;
import MODELS.ENTITY.EDUCATIONAL.Feedback;
import MODELS.ENTITY.LOGS.FeedbackLogs;
import MODELS.ENTITY.USERS.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedbackLogsRepository extends MongoRepository<FeedbackLogs, String> {
    Page<FeedbackLogs> findByActor(User actor, Pageable pageable);

    Page<FeedbackLogs> findByTarget(Feedback target, Pageable pageable);

    Page<FeedbackLogs> findByType(String type, Pageable pageable);
}
