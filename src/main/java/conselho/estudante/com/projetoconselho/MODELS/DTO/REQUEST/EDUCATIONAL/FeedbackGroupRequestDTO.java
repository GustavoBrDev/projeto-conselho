package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.ClassFeedback;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.FeedbackGroup;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.PersonalFeedback;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Date;

/**
 * DTO de requisição para criação de um {@link FeedbackGroup}
 * Contém os dados necessários para criar um grupo de feedbacks.
 * @author Camilly Chelest
 * @since 19/03/2025
 */

@Builder
public record FeedbackGroupRequestDTO(
        @NotNull
        Date date,
        @NotNull
        PersonalFeedback personalFeedbackId,
        @NotNull
        ClassFeedback classFeedbackId
) {

        public FeedbackGroup convert() {
                return FeedbackGroup.builder()
                        .date(this.date)
                        .personalFeedbackId(this.personalFeedbackId)
                        .classFeedbackId(this.classFeedbackId)
                        .build();
        }
}
