package conselho.estudante.com.projetoconselho.repositories.users;

import conselho.estudante.com.projetoconselho.models.entity.users.Supervisor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {

    public boolean existsByRegister(Long register);


    public boolean existsByEmail(String email);
    public Supervisor findByEmail(String email);

    Page<Supervisor> findByCourses_Id(Long courseId, Pageable pageable);

    //Page<Supervisor> findByClasses_Id(Long classId, Pageable pageable);

    @Query("SELECT s FROM Supervisor s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(s.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "CAST(s.register AS string) LIKE CONCAT('%', :searchTerm, '%')")
    Page<Supervisor> searchByMultipleFields(@Param("searchTerm") String searchTerm, Pageable pageable);
}
