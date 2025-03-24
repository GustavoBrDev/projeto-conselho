package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS;

import lombok.Data;

import java.util.List;

/**
 * DTO para retornar dados de Teacher ao cliente.
 * @author Alex Zastrow
 */
@Data
public class TeacherResponseDTO {

    private Long id;
    private String name;
    private String username;
    private String email;
    private String image;
    private Long register;

    /*
     * Listas de nomes de cursos, disciplinas e turnos
     */
    private List<String> courses;
    private List<String> subjects;
    private List<String> shifts;
}