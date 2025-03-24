package conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.RepresentativeLogs;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.UserLogs;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Representative;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepresentativeLogsRepository extends MongoRepository<RepresentativeLogs, String> {
    Page<RepresentativeLogs> findByActor(User actor, Pageable pageable);

    Page<UserLogs> findByTarget(Representative target, Pageable pageable);

    Page<RepresentativeLogs> findByType(String type, Pageable pageable);
}
