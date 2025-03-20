package MODELS.DTO.REQUEST;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PersonalFeedbackRequestDTO(@NotBlank String text, @NotNull Long studentId) {
}
