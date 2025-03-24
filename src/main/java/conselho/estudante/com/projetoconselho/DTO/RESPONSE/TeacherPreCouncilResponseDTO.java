<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/DTO/RESPONSE/TeacherPreCouncilResponseDTO.java
package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE;
========
package conselho.estudante.com.projetoconselho.DTO.RESPONSE;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/DTO/RESPONSE/TeacherPreCouncilResponseDTO.java

import java.util.Date;

/**
 * DTO (Data Transfer Object) para representar os dados de resposta de um pré-conselho de professores (TeacherPreCouncil).
 * Esta classe é usada para transferir informações sobre um pré-conselho, incluindo seu ID, datas de criação, início e término,
 * informações do conselho, da classe, o status de preenchimento, além dos dados do professor e da disciplina associados a ele.
 *
 * @author Cauã Justimiano Dutra
 * @since 19/03/2025
 */
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
        CouncilResponseDTO councilDTO,

        /**
         * DTO da Classe relacionada ao pré-conselho.
         */
        ClasseResponseDTO classeDTO,

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
