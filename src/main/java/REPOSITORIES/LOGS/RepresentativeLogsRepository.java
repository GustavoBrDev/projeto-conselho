package REPOSITORIES.LOGS;

import MODELS.ENTITY.LOGS.RepresentativeLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepresentativeLogsRepository extends MongoRepository<RepresentativeLogs, String> {
}
