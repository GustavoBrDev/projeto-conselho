package conselho.estudante.com.projetoconselho.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@AllArgsConstructor
@Data
@Builder
public class ErrorResponse {

    private String message;
    private Instant timestamp;
    private String type;
}
