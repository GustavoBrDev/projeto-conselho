package REPOSITORIES.LOGS;

import MODELS.ENTITY.LOGS.FeedbackLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedbackLogsRepository extends MongoRepository<FeedbackLogs, String> {
}
