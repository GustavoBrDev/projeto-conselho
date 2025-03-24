package conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.CouncilLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CouncilLogsRepository extends MongoRepository<CouncilLogs, String> {
}
