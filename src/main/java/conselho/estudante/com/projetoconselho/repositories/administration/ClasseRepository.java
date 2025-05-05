package conselho.estudante.com.projetoconselho.repositories.administration;

import conselho.estudante.com.projetoconselho.models.entity.administration.Classe;
import conselho.estudante.com.projetoconselho.models.entity.administration.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {

    public boolean existsByName(String name);
    public boolean existsByAcronym(String acronym);
    public Page<Classe> findAllByCourse(Course course, Pageable pageable);

}
