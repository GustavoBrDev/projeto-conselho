package REPOSITORIES.USERS;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repositório para a entidade {@link Advisor}, responsável por realizar operações
 * de persistência e recuperação no banco de dados para instâncias de orientadores.
 */
public interface AdvisorRepository extends JpaRepository<Advisor, Long>, JpaSpecificationExecutor<Advisor> {
    /**
     * Verifica a existência de um orientador com o email fornecido no banco de dados.
     */
    boolean existsByEmail(String email);

    /**
     * Verifica a existência de um orientador com a matrícula fornecida no banco de dados.
     */
    boolean existsByRegistration(Long registration);

    /**
     * Encontra um orientador pelo seu email.
     */
    Advisor findByEmail(String email);
}