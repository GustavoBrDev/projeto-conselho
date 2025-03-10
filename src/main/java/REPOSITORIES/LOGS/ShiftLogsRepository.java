package REPOSITORIES.LOGS;

import MODELS.ENTITY.LOGS.ShiftLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShiftLogsRepository extends MongoRepository<ShiftLogs, String> {
}
