package conselho.estudante.com.projetoconselho.repositories.logs;

import conselho.estudante.com.projetoconselho.models.entity.logs.RepresentativeLogs;
import conselho.estudante.com.projetoconselho.models.entity.users.Representative;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepresentativeLogsRepository extends MongoRepository<RepresentativeLogs, String> {
    Page<RepresentativeLogs> findByActor(User actor, Pageable pageable);

    Page<RepresentativeLogs> findByTarget(Representative target, Pageable pageable);

    Page<RepresentativeLogs> findByType(String type, Pageable pageable);
}
