package conselho.estudante.com.projetoconselho.repositories.logs;

import conselho.estudante.com.projetoconselho.models.entity.administration.Classe;
import conselho.estudante.com.projetoconselho.models.entity.logs.ClasseLogs;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClasseLogsRepository extends MongoRepository<ClasseLogs, String> {
    Page<ClasseLogs> findByActor(User actor, Pageable pageable);

    Page<ClasseLogs> findByTarget(Classe target, Pageable pageable);

    Page<ClasseLogs> findByType(String type, Pageable pageable);
}
