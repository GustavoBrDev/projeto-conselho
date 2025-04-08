package conselho.estudante.com.projetoconselho.services.users;

import conselho.estudante.com.projetoconselho.models.dto.request.USERS.AdvisorRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.users.AdvisorResponseDTO;
import conselho.estudante.com.projetoconselho.models.exceptions.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.models.entity.users.Advisor;
import conselho.estudante.com.projetoconselho.repositories.users.AdvisorRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável por gerenciar as operações relacionadas aos orientadores.
 * Inclui funcionalidades de criação, atualização, exclusão, busca e edição de atributos específicos.
 *
 * @author Alex Zastrow
 */

@Service
@AllArgsConstructor
public class AdvisorService {

    private final AdvisorRepository repository;

    /**
     * Cria um novo orientador após validação dos dados e verificação de duplicidade.
     *
     * @param advisorRequestDTO dados do novo orientador
     * @return objeto {@link AdvisorResponseDTO} representando o orientador criado
     */
    public AdvisorResponseDTO create(AdvisorRequestDTO advisorRequestDTO) {
        validateAdvisorRequest(advisorRequestDTO);
        Advisor advisor = advisorRequestDTO.convert();

        if (repository.existsByEmail(advisor.getEmail())) {
            throw new DadosDuplicadosException("Email já cadastrado");
        }

        if (repository.existsByRegister(advisor.getRegister())) {
            throw new DadosDuplicadosException("Matrícula já cadastrada");
        }

        return convertToDTO(repository.save(advisor));
    }

    /**
     * Atualiza os dados de um orientador existente.
     *
     * @param id identificador do orientador
     * @param advisorRequestDTO novos dados a serem atualizados
     * @return {@link AdvisorResponseDTO} com dados atualizados
     */
    public AdvisorResponseDTO update(Long id, AdvisorRequestDTO advisorRequestDTO) {
        validateAdvisorRequest(advisorRequestDTO);

        Advisor existingAdvisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));

        Advisor advisor = advisorRequestDTO.convert();
        advisor.setId(id);

        repository.findByEmail(advisor.getEmail())
                .ifPresent(a -> {
                    if (!a.getId().equals(id)) {
                        throw new DadosDuplicadosException("Email já cadastrado por outro orientador");
                    }
                });

        repository.findByRegister(advisor.getRegister())
                .ifPresent(a -> {
                    if (!a.getId().equals(id)) {
                        throw new DadosDuplicadosException("Matrícula já cadastrada por outro orientador");
                    }
                });

        advisor.setPassword(existingAdvisor.getPassword());
        advisor.setCreatedAt(existingAdvisor.getCreatedAt());
        advisor.setUsername(existingAdvisor.getUsername());

        return convertToDTO(repository.save(advisor));
    }

    /**
     * Edita o nome de um orientador.
     *
     * @param id identificador do orientador
     * @param name novo nome
     * @return {@link AdvisorResponseDTO} com nome atualizado
     */
    public AdvisorResponseDTO editName(Long id, String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        Advisor advisor = getAdvisorById(id);
        advisor.setName(name);
        return convertToDTO(repository.save(advisor));
    }

    /**
     * Edita o email de um orientador.
     *
     * @param id identificador do orientador
     * @param email novo email
     * @return {@link AdvisorResponseDTO} com email atualizado
     */
    public AdvisorResponseDTO editEmail(Long id, String email) {
        if (StringUtils.isBlank(email)) {
            throw new IllegalArgumentException("Email inválido");
        }

        Advisor advisor = getAdvisorById(id);

        repository.findByEmail(email)
                .ifPresent(a -> {
                    if (!a.getId().equals(id)) {
                        throw new DadosDuplicadosException("Email já cadastrado por outro orientador");
                    }
                });

        advisor.setEmail(email);
        if (advisor.getUsername().equals(advisor.getEmail())) {
            advisor.setUsername(email);
        }
        return convertToDTO(repository.save(advisor));
    }

    /**
     * Edita a matrícula de um orientador.
     *
     * @param id identificador do orientador
     * @param registration nova matrícula
     * @return {@link AdvisorResponseDTO} com matrícula atualizada
     */
    public AdvisorResponseDTO editRegistration(Long id, Long registration) {
        if (registration == null) {
            throw new IllegalArgumentException("Matrícula não pode ser nula");
        }

        Advisor advisor = getAdvisorById(id);

        repository.findByRegister(registration)
                .ifPresent(a -> {
                    if (!a.getId().equals(id)) {
                        throw new DadosDuplicadosException("Matrícula já cadastrada por outro orientador");
                    }
                });

        advisor.setRegister(registration);
        return convertToDTO(repository.save(advisor));
    }

    /**
     * Edita a senha de um orientador.
     *
     * @param id identificador do orientador
     * @param password nova senha
     * @return {@link AdvisorResponseDTO} com senha atualizada
     */
    public AdvisorResponseDTO editPassword(Long id, String password) {
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }

        Advisor advisor = getAdvisorById(id);
        return convertToDTO(repository.save(advisor));
    }

    /**
     * Edita a imagem de perfil do orientador.
     *
     * @param id identificador do orientador
     * @param image nova imagem
     * @return {@link AdvisorResponseDTO} com imagem atualizada
     */
    public AdvisorResponseDTO editImage(Long id, String image) {
        Advisor advisor = getAdvisorById(id);
        advisor.setImage(image);
        return convertToDTO(repository.save(advisor));
    }

    /**
     * Método interno para alterar a senha do orientador.
     *
     * @param advisor objeto {@link Advisor}
     * @param password nova senha
     * @return true se atualizado com sucesso, false caso contrário
     */
    public boolean editPassword(Advisor advisor, String password) {
        try {
            advisor.setPassword(password);
            repository.save(advisor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Busca todos os orientadores paginados.
     *
     * @param pageable objeto de paginação
     * @return página com orientadores encontrados
     */
    public Page<AdvisorResponseDTO> findAllAdvisors(Pageable pageable) {
        Page<Advisor> advisors = repository.findAll(pageable);
        if (advisors.isEmpty()) {
            throw new NaoEncontradoException("Nenhum orientador encontrado");
        }
        return advisors.map(this::convertToDTO);
    }

    /**
     * Busca um orientador pelo ID.
     *
     * @param id identificador
     * @return {@link AdvisorResponseDTO} correspondente
     */
    public AdvisorResponseDTO findAdvisorById(Long id) {
        return convertToDTO(getAdvisorById(id));
    }

    /**
     * Busca um orientador pelo email.
     *
     * @param email email do orientador
     * @return {@link AdvisorResponseDTO} correspondente
     */
    public AdvisorResponseDTO findAdvisorByEmail(String email) {
        return repository.findByEmail(email)
                .map(this::convertToDTO)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));
    }

    /**
     * Realiza uma busca dinâmica com termo livre e paginação.
     *
     * @param term termo de busca (nome, email ou matrícula)
     * @param pageable configuração de paginação
     * @return página com orientadores correspondentes ao filtro
     */
    public Page<AdvisorResponseDTO> searchAdvisors(String term, Pageable pageable) {
        Specification<Advisor> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(term)) {
                String likeTerm = "%" + term.toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("name")), likeTerm),
                        cb.like(cb.lower(root.get("email")), likeTerm),
                        cb.like(root.get("register").as(String.class), likeTerm)
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return repository.findAll(spec, pageable).map(this::convertToDTO);
    }

    /**
     * Remove um orientador pelo ID.
     *
     * @param id identificador do orientador
     */
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NaoEncontradoException("Orientador não encontrado");
        }
        repository.deleteById(id);
    }

    /**
     * Busca um orientador e retorna o objeto completo (uso interno).
     *
     * @param id identificador do orientador
     * @return {@link Advisor}
     */
    public Advisor getAdvisorById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));
    }

    /**
     * Valida os dados obrigatórios para criação ou atualização de um orientador.
     *
     * @param request DTO com os dados
     */
    private void validateAdvisorRequest(AdvisorRequestDTO request) {
        Assert.notNull(request, "Dados do orientador não podem ser nulos");
        Assert.hasText(request.name(), "Nome é obrigatório");
        Assert.hasText(request.email(), "Email é obrigatório");
        Assert.hasText(request.password(), "Senha é obrigatória");
        Assert.notNull(request.register(), "Matrícula é obrigatória");
    }

    /**
     * Converte um objeto {@link Advisor} para {@link AdvisorResponseDTO}
     *
     * @param advisor entidade Advisor
     * @return DTO representando o orientador
     */
    private AdvisorResponseDTO convertToDTO(Advisor advisor) {
        return AdvisorResponseDTO.builder()
                .id(advisor.getId())
                .image(advisor.getImage())
                .name(advisor.getName())
                .email(advisor.getEmail())
                .register(advisor.getRegister())
                .build();
    }

    /**
     * Método para buscar um orientador pelo email (lógica interna)
     * @param email email do orientador
     * @return orientador em forma de {@link Advisor}
     * @author Gustavo Stinghen
     * @since 31/03/2025
     */
    public Advisor getObjectAdvisor ( String email ) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new NaoEncontradoException("Orientador nao encontrado"));
    }
}