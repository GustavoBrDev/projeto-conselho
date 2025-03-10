package REPOSITORIES.LOGS;

import MODELS.ENTITY.LOGS.UserLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserLogsRepository extends MongoRepository<UserLogs, String> {
}
