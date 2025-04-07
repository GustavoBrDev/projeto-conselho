package conselho.estudante.com.projetoconselho.REPOSITORIES.USERS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Advisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * Repositório responsável pelo acesso aos dados da entidade {@link Advisor}.
 *
 * Estende {@link JpaRepository} para operações CRUD padrão e {@link JpaSpecificationExecutor}
 * para permitir buscas dinâmicas com critérios personalizados (Specifications).
 *
 * Contém métodos auxiliares para validação e busca por campos únicos como email e matrícula.
 *
 * @author Alex Zastrow
 */
public interface AdvisorRepository extends JpaRepository<Advisor, Long>, JpaSpecificationExecutor<Advisor> {

    /**
     * Verifica se já existe um orientador cadastrado com o email informado.
     *
     * @param email Email a ser verificado.
     * @return {@code true} se já existir, {@code false} caso contrário.
     */
    boolean existsByEmail(String email);

    /**
     * Verifica se já existe um orientador cadastrado com a matrícula (registro) informada.
     *
     * @param register Matrícula a ser verificada.
     * @return {@code true} se já existir, {@code false} caso contrário.
     */
    boolean existsByRegister(Long register);

    /**
     * Busca um orientador pelo email.
     *
     * @param email Email do orientador.
     * @return Um {@link Optional} contendo o orientador, se encontrado.
     */
    Optional<Advisor> findByEmail(String email);

    /**
     * Busca um orientador pela matrícula (registro).
     *
     * @param register Matrícula do orientador.
     * @return Um {@link Optional} contendo o orientador, se encontrado.
     */
    Optional<Advisor> findByRegister(Long register);
}