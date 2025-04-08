package conselho.estudante.com.projetoconselho.repositories.logs;

import conselho.estudante.com.projetoconselho.models.entity.logs.UserLogs;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserLogsRepository extends MongoRepository<UserLogs, String> {
    Page<UserLogs> findByActor(User actor, Pageable pageable);
    Page<UserLogs> findByTarget(User target, Pageable pageable);
    Page<UserLogs> findByType(String type, Pageable pageable);
}
