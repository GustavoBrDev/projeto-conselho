package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepresentativeResponseDTO {
    private Long id;
    private List<Student> students;
    private Classe representativeOf;
}
