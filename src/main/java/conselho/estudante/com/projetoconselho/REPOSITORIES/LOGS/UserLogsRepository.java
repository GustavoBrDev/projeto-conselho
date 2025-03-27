package conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.UserLogs;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserLogsRepository extends MongoRepository<UserLogs, String> {
    Page<UserLogs> findByActor(User actor, Pageable pageable);
    Page<UserLogs> findByTarget(User target, Pageable pageable);
    Page<UserLogs> findByType(String type, Pageable pageable);
}
