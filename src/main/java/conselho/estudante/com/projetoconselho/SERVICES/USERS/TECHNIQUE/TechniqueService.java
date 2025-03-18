package conselho.estudante.com.projetoconselho.SERVICES.USERS.TECHNIQUE;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS.TechniqueRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.TechniqueResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Technique;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.USERS.TechniqueRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Serviço para gerenciar operações relacionadas à entidade {@link Technique}.
 *
 * @author Joana Voigt
 * @since 17/03/2025
 *
 * @see Technique
 * @see TechniqueRequestDTO
 * @see TechniqueResponseDTO
 */

@Service
@AllArgsConstructor
public class TechniqueService {

    private TechniqueRepository repository;

    /**
     * Cria uma nova técnica pedagogica.
     *
     * @param techniqueRequestDTO Dados da técnica a serem criados.
     * @return DTO da resposta contendo a técnica criada.
     * @throws DadosDuplicadosException se o email ou registro já existirem.
     */
    public TechniqueResponseDTO create(TechniqueRequestDTO techniqueRequestDTO) {
        Technique technique = techniqueRequestDTO.convert();
        if(repository.existsByEmail(technique.getEmail())) {
            throw new DadosDuplicadosException("Email ja cadastrado");
        } else if (repository.existsByRegister(technique.getRegister())) {
            throw new DadosDuplicadosException("Registro ja cadastrado");
        } else {
            return repository.save(technique).toDTO();
        }
    }

    /**
     * Atualiza uma técnica pedagogica existente.
     *
     * @param id ID da técnica a ser atualizada.
     * @param techniqueRequestDTO Novos dados da técnica.
     * @return DTO da resposta contendo a técnica atualizada.
     * @throws DadosDuplicadosException se o email ou registro já existirem ou se a técnica não for encontrada.
     */
    public TechniqueResponseDTO update(Long id, TechniqueRequestDTO techniqueRequestDTO) {
        Technique technique = techniqueRequestDTO.convert();
        if (repository.existsById(id)) {
            technique.setId(id);
            if (repository.existsByEmail(technique.getEmail())) {
                throw new DadosDuplicadosException("Email ja cadastrado");
            } else if (repository.existsByRegister(technique.getRegister())) {
                throw new DadosDuplicadosException("Registro ja cadastrado");
            }
            return repository.save(technique).toDTO();
        }
        throw new NaoEncontradoException("Técnico nao encontrado");
    }

    /**
     * Edita o nome de uma técnica específica.
     *
     * @param id ID da técnica.
     * @param name Novo nome.
     * @return DTO da resposta contendo a técnica atualizada.
     */
    public TechniqueResponseDTO editName(Long id, String name) {
        Technique technique = repository.findById(id).get();
        technique.setName(name);
        return repository.save(technique).toDTO();
    }

    /**
     * Edita o email de uma técnica específica.
     *
     * @param id ID da técnica.
     * @param email Novo email.
     * @return DTO da resposta contendo a técnica atualizada.
     */
    public TechniqueResponseDTO editEmail(Long id, String email) {
        Technique technique = repository.findById(id).get();
        technique.setEmail(email);
        return repository.save(technique).toDTO();
    }

    /**
     * Edita o número de registro de uma técnica específica.
     *
     * @param id ID da técnica.
     * @param register Novo número de registro.
     * @return DTO da resposta contendo a técnica atualizada.
     */
    public TechniqueResponseDTO editRegister(Long id, Long register) {
        Technique technique = repository.findById(id).get();
        technique.setRegister(register);
        return repository.save(technique).toDTO();
    }

    /**
     * Edita a senha de uma técnica específica.
     *
     * @param id ID da técnica.
     * @param password Nova senha.
     * @return DTO da resposta contendo a técnica atualizada.
     */
    public TechniqueResponseDTO editPassword(Long id, String password) {
        Technique technique = repository.findById(id).get();
        technique.setPassword(password);
        return repository.save(technique).toDTO();
    }

    /**
     * Edita a imagem de uma técnica específica.
     *
     * @param id ID da técnica.
     * @param image Nova imagem.
     * @return DTO da resposta contendo a técnica atualizada.
     */
    public TechniqueResponseDTO editImage(Long id, String image) {
        Technique technique = repository.findById(id).get();
        technique.setImage(image);
        return repository.save(technique).toDTO();
    }

    /**
     * Mostra uma lista paginada de técnicas.
     *
     * @param pageable Informações de paginação.
     * @return Página de técnicas na forma de DTOs de resposta.
     * @throws NaoEncontradoException se nenhuma técnica for encontrada.
     */
    public Page<TechniqueResponseDTO> findTechniques(Pageable pageable) {
        try {
            return repository.findAll(pageable).map(Technique::toDTO);
        } catch (Exception e) {
            throw new NaoEncontradoException("Técnicos nao encontrados");
        }
    }

    /**
     * Busca uma técnica pelo seu ID.
     *
     * @param id ID da técnica.
     * @return DTO da resposta contendo a técnica encontrada.
     * @throws NaoEncontradoException se a técnica não for encontrada.
     */
    public TechniqueResponseDTO findTechniqueById(Long id) {
        try {
            return repository.findById(id).get().toDTO();
        } catch (Exception e) {
            throw new NaoEncontradoException("Técnico nao encontrado");
        }
    }

    /**
     * Busca uma técnica pelo seu email.
     *
     * @param email Email da técnica.
     * @return DTO da resposta contendo a técnica encontrada.
     * @throws NaoEncontradoException se a técnica não for encontrada.
     */
    public TechniqueResponseDTO findTechniqueByEmail(String email) {
        try {
            return repository.findByEmail(email).toDTO();
        } catch (Exception e) {
            throw new NaoEncontradoException("Técnico nao encontrado");
        }
    }

    /**
     * Deleta uma técnica pelo seu ID.
     *
     * @param id ID da técnica a ser deletada.
     * @throws NaoEncontradoException se a técnica não for deletada corretamente.
     */
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NaoEncontradoException("Técnico nao deletado");
        }
    }

    /**
     * Filtra técnicas baseadas em um termo de busca.
     *
     * @param termo Termo a ser buscado (pode ser nome, email ou registro).
     * @param pageable Informações de paginação.
     * @return Página de técnicas que correspondem ao termo de busca na forma de DTOs de resposta.
     */
    public Page<TechniqueResponseDTO> TechniqueFilter(String termo, Pageable pageable) {
        return repository.findAll(TechniqueSpecification.techniqueFilter(termo), pageable)
                .map(Technique::toDTO);
    }
}
