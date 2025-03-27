package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ADMINISTRATION.SubjectResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.TeacherResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO (Data Transfer Object) para representar os dados de resposta de um pré-conselho de professores (TeacherPreCouncil).
 * Esta classe é usada para transferir informações sobre um pré-conselho, incluindo seu ID, datas de criação, início e término,
 * informações do conselho, da classe, o status de preenchimento, além dos dados do professor e da disciplina associados a ele.
 *
 * @author Cauã Justimiano Dutra
 * @since 19/03/2025
 */
@AllArgsConstructor
@Builder
public record TeacherPreCouncilResponseDTO(

        /**
         * Identificador único do pré-conselho de professores.
         */
        Long id,

        /**
         * Data de criação do pré-conselho.
         */
        Date createdAt,

        /**
         * Data de início do pré-conselho.
         */
        Date startDate,

        /**
         * Data de término do pré-conselho.
         */
        Date endDate,

        /**
         * DTO do Conselho ao qual o pré-conselho está vinculado.
         */
        Council councilDTO,

        /**
         * DTO da Classe relacionada ao pré-conselho.
         */
        Classe classeDTO,

        /**
         * Indica se o pré-conselho foi preenchido pelo professor.
         */
        Boolean isFilled,

        /**
         * DTO do Professor responsável pelo pré-conselho.
         */
        TeacherResponseDTO teacherDTO,

        /**
         * DTO da Disciplina associada ao pré-conselho.
         */
        SubjectResponseDTO subjectDto
) {
}
