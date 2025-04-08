package conselho.estudante.com.projetoconselho.models.dto.request.EDUCATIONAL;
import conselho.estudante.com.projetoconselho.models.entity.administration.Classe;
import conselho.estudante.com.projetoconselho.models.entity.administration.Subject;
import conselho.estudante.com.projetoconselho.models.entity.educational.Council;
import conselho.estudante.com.projetoconselho.models.entity.educational.TeacherPreCouncil;
import conselho.estudante.com.projetoconselho.models.entity.users.Teacher;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.util.Date;

/**
 * DTO (Data Transfer Object) para representar os dados de entrada ao criar um pré-conselho de professores (TeacherPreCouncil).
 * Esta classe é usada para transferir informações do pré-conselho para a camada de serviço ou controlador.
 *
 * @author Cauã Justimiano Dutra
 * @since 19/03/2025
 */
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
