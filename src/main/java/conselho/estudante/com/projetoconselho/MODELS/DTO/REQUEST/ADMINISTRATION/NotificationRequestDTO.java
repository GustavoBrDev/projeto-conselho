package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.ADMINISTRATION;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO (Data Transfer Object) para representar os dados de entrada ao criar ou atualizar uma notificação.
 * Esta classe é usada para transferir informações da notificação para a camada de serviço ou controlador.
 *
 * @author Camilly Chelest
 * @since 18/03/2025
*/
public record NotificationRequestDTO(

        @NotBlank
        String message,
        @NotNull
        Boolean isUrgent

) {
}
