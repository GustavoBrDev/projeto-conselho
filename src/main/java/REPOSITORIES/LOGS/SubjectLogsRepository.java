package REPOSITORIES.LOGS;

import MODELS.ENTITY.LOGS.SubjectLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubjectLogsRepository extends MongoRepository<SubjectLogs, String> {
}
