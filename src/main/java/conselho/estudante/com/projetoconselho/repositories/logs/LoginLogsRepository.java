package conselho.estudante.com.projetoconselho.repositories.logs;

import conselho.estudante.com.projetoconselho.models.entity.logs.LoginLogs;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoginLogsRepository extends MongoRepository<LoginLogs, String> {
    Page<LoginLogs> findByUser(User user, Pageable pageable);

    boolean existsByUser(User user);
}
