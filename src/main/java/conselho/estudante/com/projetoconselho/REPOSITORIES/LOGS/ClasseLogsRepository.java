package conselho.estudante.com.projetoconselho.REPOSITORIES.LOGS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.ClasseLogs;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.UserLogs;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClasseLogsRepository extends MongoRepository<ClasseLogs, String> {
    Page<ClasseLogs> findByActor(User actor, Pageable pageable);

    Page<ClasseLogs> findByTarget(Classe target, Pageable pageable);

    Page<ClasseLogs> findByType(String type, Pageable pageable);
}
