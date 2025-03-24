package conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.CourseLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseLogsRepository extends MongoRepository<CourseLogs, String> {
}
