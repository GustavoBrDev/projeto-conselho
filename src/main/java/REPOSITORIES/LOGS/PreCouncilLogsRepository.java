package REPOSITORIES.LOGS;

import MODELS.ENTITY.LOGS.PreCouncilLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PreCouncilLogsRepository extends MongoRepository<PreCouncilLogs, String> {
}
