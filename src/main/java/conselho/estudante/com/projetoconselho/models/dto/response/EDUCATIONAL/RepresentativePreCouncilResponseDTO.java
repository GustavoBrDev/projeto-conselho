package conselho.estudante.com.projetoconselho.models.dto.response.EDUCATIONAL;


import conselho.estudante.com.projetoconselho.models.entity.administration.Classe;
import conselho.estudante.com.projetoconselho.models.entity.educational.*;
import conselho.estudante.com.projetoconselho.models.entity.users.Teacher;


import java.util.Date;
import java.util.List;


public record RepresentativePreCouncilResponseDTO(
        Long id,
        Council council,
        Date createdAt,
        Date startDate,
        Date endDate,
        Classe classe,
        Boolean isFilled,
        List<Teacher> teachers,
        AdvisorFeeback advisorFeedback,
        SupervisorFeedback supervisorFeedback,
        List<TeacherFeeback> teacherFeedbacks,
        List<ItemFeedback> itemFeedbacks
) {
}
