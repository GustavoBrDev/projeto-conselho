package conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.RepresentativeLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepresentativeLogsRepository extends MongoRepository<RepresentativeLogs, String> {
}
