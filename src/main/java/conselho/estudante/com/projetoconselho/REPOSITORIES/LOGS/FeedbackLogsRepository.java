package conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.FeedbackLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedbackLogsRepository extends MongoRepository<FeedbackLogs, String> {
}
