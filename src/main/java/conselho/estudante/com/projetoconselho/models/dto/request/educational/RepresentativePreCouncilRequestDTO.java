package conselho.estudante.com.projetoconselho.models.dto.request.educational;


import conselho.estudante.com.projetoconselho.models.entity.educational.AvaliableTeacher;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


import java.util.Date;
import java.util.List;


@Builder
public record RepresentativePreCouncilRequestDTO(
        @NotNull Long councilId,
        @NotNull Date startDate,
        @NotNull Date endDate,
        @NotNull Long classeId,
        List<AvaliableTeacher> teachers,
        AdvisorFeedbackRequestDTO advisorFeedback,
        SupervisorFeedbackRequestDTO supervisorFeedback,
        List<ItemFeedbackRequestDTO> itemFeedback
) {
}
