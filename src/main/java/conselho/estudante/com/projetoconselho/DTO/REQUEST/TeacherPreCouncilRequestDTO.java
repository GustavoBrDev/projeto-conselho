<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/DTO/REQUEST/TeacherPreCouncilRequestDTO.java
package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST;
========
package conselho.estudante.com.projetoconselho.DTO.REQUEST;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/DTO/REQUEST/TeacherPreCouncilRequestDTO.java

import jakarta.validation.constraints.NotNull;

/**
 * DTO (Data Transfer Object) para representar os dados de entrada ao criar um pré-conselho de professores (TeacherPreCouncil).
 * Esta classe é usada para transferir informações do pré-conselho para a camada de serviço ou controlador.
 *
 * @author Cauã Justimiano Dutra
 * @since 19/03/2025
 */
public record TeacherPreCouncilRequestDTO(

        /**
         * Identificador do professor associado ao pré-conselho.
         * Não pode ser nulo.
         */
        @NotNull
        Long teacher_id
) {
}
