package REPOSITORIES.LOGS;

import MODELS.ENTITY.EDUCATIONAL.PreCouncil;
import MODELS.ENTITY.LOGS.PreCouncilLogs;
import MODELS.ENTITY.USERS.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PreCouncilLogsRepository extends MongoRepository<PreCouncilLogs, String> {
    Page<PreCouncilLogs> findByActor(User actor, Pageable pageable);

    Page<PreCouncilLogs> findByTarget(PreCouncil target, Pageable pageable);

    Page<PreCouncilLogs> findByType(String type, Pageable pageable);
}
