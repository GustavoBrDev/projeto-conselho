package conselho.estudante.com.projetoconselho.models.dto.request.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.models.entity.administration.Shift;
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
