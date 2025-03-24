<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/DTO/RESPONSE/ShiftResponseDTO.java
package conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE;
========
package conselho.estudante.com.projetoconselho.DTO.RESPONSE;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/DTO/RESPONSE/ShiftResponseDTO.java

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
