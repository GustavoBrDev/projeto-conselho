package conselho.estudante.com.projetoconselho.REPOSITORIES.USERS;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Technique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repositório para a entidade {@link Technique}, responsável por realizar operações
 * de persistência e recuperação no banco de dados para instâncias de técnicos.
 *
 * @author Joana Voigt
 * @since 17/03/2025
 *
 * @see Technique
 */
public interface TechniqueRepository extends JpaRepository<Technique, Long>, JpaSpecificationExecutor<Technique> {
    /**
     * Verifica a existência de um técnico com o email fornecido no banco de dados.
     *
     * @param email Email a ser verificado.
     * @return true se um técnico com o email fornecido existe, caso contrário, false.
     */

    public boolean existsByEmail(String email);
    /**
     * Verifica a existência de um técnico com o registro fornecido no banco de dados.
     *
     * @param register Registro a ser verificado.
     * @return true se um técnico com o registro fornecido existe, caso contrário, false.
     */
    public boolean existsByRegister(Long register);

    /**
     * Encontra um técnico pelo seu email.
     *
     * @param email Email do técnico a ser encontrado.
     * @return A entidade {@link Technique} que corresponde ao email fornecido,
     *         ou null se nenhum técnico for encontrado.
     */
    public Technique findByEmail(String email);
}