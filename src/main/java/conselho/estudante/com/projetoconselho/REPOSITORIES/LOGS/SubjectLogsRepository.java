package conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.SubjectLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubjectLogsRepository extends MongoRepository<SubjectLogs, String> {
}
