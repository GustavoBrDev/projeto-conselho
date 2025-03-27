package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL;


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
        List<Long> teacherIds,
        Long advisorFeedbackId,
        Long supervisorFeedbackId,
        List<Long> itemFeedbackIds
) {
}
