package REPOSITORIES.LOGS;

import MODELS.ENTITY.ADMINISTRATION.Classe;
import MODELS.ENTITY.LOGS.ClasseLogs;
import MODELS.ENTITY.LOGS.UserLogs;
import MODELS.ENTITY.USERS.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClasseLogsRepository extends MongoRepository<ClasseLogs, String> {
    Page<UserLogs> findByActor(User actor, Pageable pageable);

    Page<UserLogs> findByTarget(Classe target, Pageable pageable);

    Page<UserLogs> findByType(String type, Pageable pageable);
}
