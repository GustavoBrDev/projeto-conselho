package REPOSITORIES.LOGS;

import MODELS.ENTITY.ADMINISTRATION.Shift;
import MODELS.ENTITY.LOGS.ShiftLogs;
import MODELS.ENTITY.USERS.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShiftLogsRepository extends MongoRepository<ShiftLogs, String> {
    Page<ShiftLogs> findByActor(User actor, Pageable pageable);

    Page<ShiftLogs> findByTarget(Shift target, Pageable pageable);

    Page<ShiftLogs> findByType(String type, Pageable pageable);
}
