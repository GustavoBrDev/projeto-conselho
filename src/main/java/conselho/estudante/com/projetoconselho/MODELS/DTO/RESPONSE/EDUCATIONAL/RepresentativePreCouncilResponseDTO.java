package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL;


import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.*;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;


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
