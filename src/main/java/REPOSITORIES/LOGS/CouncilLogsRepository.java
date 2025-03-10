package REPOSITORIES.LOGS;

import MODELS.ENTITY.LOGS.CouncilLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CouncilLogsRepository extends MongoRepository<CouncilLogs, String> {
}
