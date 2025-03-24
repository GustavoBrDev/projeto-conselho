package conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.ShiftLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShiftLogsRepository extends MongoRepository<ShiftLogs, String> {
}
