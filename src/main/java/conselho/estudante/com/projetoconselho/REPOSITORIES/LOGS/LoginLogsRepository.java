package conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.LoginLogs;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoginLogsRepository extends MongoRepository<LoginLogs, String> {
    Page<LoginLogs> findByUser(User user, Pageable pageable);

    boolean existsByUser(User user);
}
