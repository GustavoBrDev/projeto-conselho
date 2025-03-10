package REPOSITORIES.LOGS;

import MODELS.ENTITY.LOGS.CourseLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseLogsRepository extends MongoRepository<CourseLogs, String> {
}
