package conselho.estudante.com.projetoconselho.REPOSITORIES.USERS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Representative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para operações com a entidade Representative
 */
@Repository
public interface RepresentativeRepository extends JpaRepository<Representative, Long> {

    /**
     * Verifica se existe representação para uma classe
     * @param classeId ID da classe
     * @return true se existir representação para a classe
     */
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
            "FROM Representative r WHERE r.representativeOf.id = :classeId")
    boolean existsByClasseId(@Param("classeId") Long classeId);

    /**
     * Verifica se um estudante é representante em qualquer classe
     * @param studentId ID do estudante
     * @return true se o estudante for representante
     */
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
            "FROM Representative r JOIN r.students s WHERE s.id = :studentId")
    boolean existsByStudentId(@Param("studentId") Long studentId);
}