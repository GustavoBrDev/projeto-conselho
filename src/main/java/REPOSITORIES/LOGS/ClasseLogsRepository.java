package REPOSITORIES.LOGS;

import MODELS.ENTITY.LOGS.ClasseLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClasseLogsRepository extends MongoRepository<ClasseLogs, String> {
}
