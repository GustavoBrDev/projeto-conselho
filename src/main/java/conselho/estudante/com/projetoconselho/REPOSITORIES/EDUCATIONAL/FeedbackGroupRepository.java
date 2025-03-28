package conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.FeedbackGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackGroupRepository extends JpaRepository<FeedbackGroup, Long> {

    public Page<FeedbackGroup> findByPersonalFeedback_Student_Id(Long studentId, Pageable pageable);
    public Page<FeedbackGroup> findByClassFeedback_Classe_Id(Long classId, Pageable pageable);
    public Page<FeedbackGroup> findByClassFeedback_Council_Id(Long councilId, Pageable pageable);
}
