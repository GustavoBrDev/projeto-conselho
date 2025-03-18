package conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.CouncilLogs;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CouncilLogsRepository extends MongoRepository<CouncilLogs, String> {
    Page<CouncilLogs> findByActor(User actor, Pageable pageable);

    Page<CouncilLogs> findByTarget(Council target, Pageable pageable);

    Page<CouncilLogs> findByType(String type, Pageable pageable);
}
