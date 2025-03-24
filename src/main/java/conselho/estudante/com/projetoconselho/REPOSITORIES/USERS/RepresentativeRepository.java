package conselho.estudante.com.projetoconselho.REPOSITORIES.USERS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Representative;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepresentativeRepository extends JpaRepository<Representative, Long> {
    Page<Representative> findAll(Pageable pageable);
    Optional<Representative> findByRepresentativeOfId(Long classeId);
    boolean existsByRepresentativeOfId(Long classeId);
}
