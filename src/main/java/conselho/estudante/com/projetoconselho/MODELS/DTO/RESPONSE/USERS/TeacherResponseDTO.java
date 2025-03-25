package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO para retornar dados de Teacher ao cliente.
 * @author Alex Zastrow
 */
@Data
@Builder
@AllArgsConstructor
public class TeacherResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String image;
    private Long register;

}