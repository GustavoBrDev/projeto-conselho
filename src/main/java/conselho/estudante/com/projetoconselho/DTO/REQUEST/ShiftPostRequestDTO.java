<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/DTO/REQUEST/ShiftPostRequestDTO.java
package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Shift;
========
package conselho.estudante.com.projetoconselho.DTO.REQUEST;

import conselho.estudante.com.projetoconselho.ENTITY.ADMINISTRATION.Shift;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/DTO/REQUEST/ShiftPostRequestDTO.java
import jakarta.validation.constraints.NotBlank;

/**
 * DTO (Data Transfer Object) para representar os dados de entrada ao criar ou atualizar um turno (Shift).
 * Esta classe é usada para transferir informações do turno para a camada de serviço ou controlador.
 *
 * @author Cauã Justimiano Dutra
 * @since 17/03/2025
 */
public record ShiftPostRequestDTO(

        /**
         * Nome do turno.
         * Não pode ser nulo ou em branco.
         */
        @NotBlank
        String name
) {

    /**
     * Converte este DTO para a entidade Shift.
     *
     * @return uma nova instância da entidade Shift com os dados deste DTO.
     */
    public Shift toEntity() {
        return Shift.builder()
                .name(this.name)
                .build();
    }
}
