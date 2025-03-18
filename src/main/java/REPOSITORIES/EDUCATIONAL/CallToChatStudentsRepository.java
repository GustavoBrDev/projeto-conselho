package REPOSITORIES.EDUCATIONAL;

import MODELS.ENTITY.EDUCATIONAL.CallToChatStudents;
import MODELS.ENTITY.EDUCATIONAL.Council;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallToChatStudentsRepository extends JpaRepository<CallToChatStudents, Long> {

    CallToChatStudents findByCouncil(Council council);

}
