package conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Advisor;
import java.util.Date;

/**
 * DTO de requisição utilizado para criação ou atualização de um orientador.
 *
 * Essa classe é utilizada na entrada de dados da API (como no cadastro ou edição de um orientador),
 * garantindo uma estrutura clara e validável.
 *
 * Utiliza a estrutura de {@code record} do Java, proporcionando imutabilidade e concisão.
 *
 * @param name     Nome completo do orientador.
 * @param email    Endereço de email do orientador.
 * @param password Senha do orientador.
 * @param register Número de matrícula (registro funcional) do orientador.
 *
 * @author Alex Zastrow
 */
public record AdvisorRequestDTO(String name, String email, String password, Long register) {

    /**
     * Converte este DTO em uma instância da entidade {@link Advisor}.
     *
     * Os campos {@code createdAt} e {@code username} são definidos automaticamente,
     * sendo {@code createdAt} com a data atual e {@code username} igual ao email.
     *
     * @return Uma instância de {@link Advisor} com os dados fornecidos.
     */
    public Advisor convert() {
        return Advisor.builder()
                .name(name)
                .email(email)
                .password(password)
                .register(register)
                .createdAt(new Date())
                .username(email)
                .build();
    }
}