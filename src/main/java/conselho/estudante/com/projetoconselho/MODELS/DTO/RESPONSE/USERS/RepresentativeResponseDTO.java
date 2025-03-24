package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;

import java.util.List;

/**
 * @param id
 * @param students
 * @param representativeOf
 */

public record RepresentativeResponseDTO(
        Long id,
        List<Student> students,
        Classe representativeOf
) {
}