package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Subject;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.TeacherPreCouncil;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.Date;

/**
 * DTO (Data Transfer Object) para representar os dados de entrada ao criar um pré-conselho de professores (TeacherPreCouncil).
 * Esta classe é usada para transferir informações do pré-conselho para a camada de serviço ou controlador.
 *
 * @author Cauã Justimiano Dutra
 * @since 19/03/2025
 */
@AllArgsConstructor
@Builder
public record TeacherPreCouncilRequestDTO(

        /**
         * Identificador do professor associado ao pré-conselho.
         * Não pode ser nulo.
         */
        @NotNull
        Teacher teacher,

        @NotNull
        Council council,

        @NotNull
        Subject subject,

        @NotNull
        Date endDate,

        @NotNull
        Classe classe
) {

        public TeacherPreCouncil toEntity() {
                return TeacherPreCouncil.builder()
                        .teacher(teacher)
                        .council(council)
                        .subject(subject)
                        .endDate(endDate)
                        .classe(classe)
                        .build();
        }
}
