package conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.UserLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserLogsRepository extends MongoRepository<UserLogs, String> {
}
