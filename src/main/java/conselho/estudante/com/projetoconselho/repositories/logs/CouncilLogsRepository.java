package conselho.estudante.com.projetoconselho.repositories.logs;

import conselho.estudante.com.projetoconselho.models.entity.educational.Council;
import conselho.estudante.com.projetoconselho.models.entity.logs.CouncilLogs;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CouncilLogsRepository extends MongoRepository<CouncilLogs, String> {
    Page<CouncilLogs> findByActor(User actor, Pageable pageable);

    Page<CouncilLogs> findByTarget(Council target, Pageable pageable);

    Page<CouncilLogs> findByType(String type, Pageable pageable);
}
