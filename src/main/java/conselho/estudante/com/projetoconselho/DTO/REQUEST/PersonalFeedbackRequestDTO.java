<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/DTO/REQUEST/PersonalFeedbackRequestDTO.java
package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST;
========
package conselho.estudante.com.projetoconselho.DTO.REQUEST;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/DTO/REQUEST/PersonalFeedbackRequestDTO.java

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PersonalFeedbackRequestDTO(@NotBlank String text, @NotNull Long studentId) {
}
