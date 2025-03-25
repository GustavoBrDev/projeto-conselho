package conselho.estudante.com.projetoconselho.REPOSITORIES.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClasseRepository extends JpaRepository<Classe, Long> {

    public boolean existsByName(String name);
    public boolean existsByAcronym(String acronym);
    public Page<Classe> findAllByCourse(Course course, Pageable pageable);

}
