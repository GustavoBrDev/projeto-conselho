package conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.RepresentativePreCouncil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RepresentativePreCouncilRepository extends
        JpaRepository<RepresentativePreCouncil, Long>,
        JpaSpecificationExecutor<RepresentativePreCouncil> {

    @Query("SELECT p FROM RepresentativePreCouncil p WHERE p.classe.id = :classeId")
    Page<RepresentativePreCouncil> findByClasse(@Param("classeId") Long classeId, Pageable pageable);

    @Query("SELECT DISTINCT p FROM RepresentativePreCouncil p " +
            "LEFT JOIN p.classe c " +
            "LEFT JOIN p.council co " +
            "LEFT JOIN p.teachers t " +
            "WHERE (LOWER(c.name) LIKE LOWER(CONCAT('%', :term, '%')) " +
            "OR CAST(co.id AS string) LIKE CONCAT('%', :term, '%') " +
            "OR LOWER(t.name) LIKE LOWER(CONCAT('%', :term, '%'))")
    Page<RepresentativePreCouncil> search(@Param("term") String term, Pageable pageable);

    @Query("SELECT p FROM RepresentativePreCouncil p WHERE " +
            "(p.startDate BETWEEN :startDate AND :endDate) OR " +
            "(p.endDate BETWEEN :startDate AND :endDate) OR " +
            "(p.startDate <= :startDate AND p.endDate >= :endDate)")
    Page<RepresentativePreCouncil> findByDateRange(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            Pageable pageable);

    @Query("SELECT p FROM RepresentativePreCouncil p WHERE p.isFilled = :isFilled")
    Page<RepresentativePreCouncil> findByFillStatus(
            @Param("isFilled") Boolean isFilled,
            Pageable pageable);

    @Query("SELECT p FROM RepresentativePreCouncil p WHERE " +
            "p.classe.id = :classeId AND p.isFilled = :isFilled")
    Page<RepresentativePreCouncil> findByClasseAndFillStatus(
            @Param("classeId") Long classeId,
            @Param("isFilled") Boolean isFilled,
            Pageable pageable);

    boolean existsByClasseId(Long classeId);
}