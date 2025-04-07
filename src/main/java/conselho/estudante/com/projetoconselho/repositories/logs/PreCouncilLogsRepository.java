package conselho.estudante.com.projetoconselho.repositories.logs;

import conselho.estudante.com.projetoconselho.models.entity.educational.PreCouncil;
import conselho.estudante.com.projetoconselho.models.entity.logs.PreCouncilLogs;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PreCouncilLogsRepository extends MongoRepository<PreCouncilLogs, String> {
    Page<PreCouncilLogs> findByActor(User actor, Pageable pageable);

    Page<PreCouncilLogs> findByTarget(PreCouncil target, Pageable pageable);

    Page<PreCouncilLogs> findByType(String type, Pageable pageable);
}
