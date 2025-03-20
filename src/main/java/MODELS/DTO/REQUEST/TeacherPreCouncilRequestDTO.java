package MODELS.DTO.REQUEST;

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
