package MODELS.DTO.response;

import java.util.Date;
import java.util.List;

/**
 * DTO (Data Transfer Object) para representar os dados de resposta de um turno (Shift).
 * Esta classe é usada para transferir informações sobre um turno, incluindo seu ID, nome,
 * data de criação e listas de IDs de professores e cursos associados a ele.
 *
 * @author Cauã Justimiano Dutra
 * @since 17/03/2025
 */
public record ShiftResponseDTO(

        /**
         * Identificador único do turno.
         */
        Long id,

        /**
         * Nome do turno.
         */
        String name,

        /**
         * Data de criação do turno.
         */
        Date createdAt,

        /**
         * Lista de IDs dos professores associados a este turno.
         */
        List<Long> teacher_id,

        /**
         * Lista de IDs dos cursos associados a este turno.
         */
        List<Long> course_id
) {
}
