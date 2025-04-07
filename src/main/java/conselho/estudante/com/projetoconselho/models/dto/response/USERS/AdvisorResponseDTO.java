package conselho.estudante.com.projetoconselho.models.dto.response.USERS;

import lombok.Builder;

/**
 * DTO de resposta utilizado para retornar os dados de um orientador.
 * Essa classe é usada principalmente nas respostas das APIs relacionadas ao cadastro,
 * atualização e consulta de orientadores no sistema.
 *
 * Utiliza o padrão Builder (via Lombok) para facilitar a criação de instâncias.
 *
 * @author Alex Zastrow
 */
@Builder
public class AdvisorResponseDTO implements UserResponseDTO {

        /**
         * Identificador único do orientador.
         */
        private Long id;

        /**
         * URL ou caminho da imagem de perfil do orientador.
         */
        private String image;

        /**
         * Nome completo do orientador.
         */
        private String name;

        /**
         * Endereço de email do orientador.
         */
        private String email;

        /**
         * Número de matrícula (registro funcional) do orientador.
         */
        private Long register;
}