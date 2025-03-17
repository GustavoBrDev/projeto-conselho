package REPOSITORIES.LOGS;

import MODELS.ENTITY.EDUCATIONAL.Council;
import MODELS.ENTITY.LOGS.CouncilLogs;
import MODELS.ENTITY.USERS.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CouncilLogsRepository extends MongoRepository<CouncilLogs, String> {
    Page<CouncilLogs> findByActor(User actor, Pageable pageable);

    Page<CouncilLogs> findByTarget(Council target, Pageable pageable);

    Page<CouncilLogs> findByType(String type, Pageable pageable);
}
