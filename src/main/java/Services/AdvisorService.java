package Services;

import MODELS.ENTITY.DTO.REQUEST.AdvisorRequestDTO;
import MODELS.ENTITY.DTO.RESPONSE.AdvisorResponseDTO;
import MODELS.ENTITY.USERS.Advisor;
import MODELS.EXCEPTIONS.DadosDuplicadosException;
import MODELS.EXCEPTIONS.NaoEncontradoException;
import REPOSITORIES.USERS.AdvisorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Serviço para gerenciar operações relacionadas à entidade {@link Advisor}.
 */
@Service
@AllArgsConstructor
public class AdvisorService {

    private final AdvisorRepository repository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Cria um novo orientador.
     */
    public AdvisorResponseDTO create(AdvisorRequestDTO advisorRequestDTO) {
        Advisor advisor = advisorRequestDTO.convert();

        if(repository.existsByEmail(advisor.getEmail())) {
            throw new DadosDuplicadosException("Email já cadastrado");
        }

        if(repository.existsByRegistration(advisor.getRegistration())) {
            throw new DadosDuplicadosException("Matrícula já cadastrada");
        }

        // Criptografa a senha antes de salvar
        advisor.setPassword(passwordEncoder.encode(advisor.getPassword()));

        return repository.save(advisor).toDTO();
    }

    /**
     * Atualiza um orientador existente.
     */
    public AdvisorResponseDTO update(Long id, AdvisorRequestDTO advisorRequestDTO) {
        if (!repository.existsById(id)) {
            throw new NaoEncontradoException("Orientador não encontrado");
        }

        Advisor advisor = advisorRequestDTO.convert();
        advisor.setId(id);

        // Verifica se o email já está em uso por outro orientador
        Advisor existingWithEmail = repository.findByEmail(advisor.getEmail());
        if (existingWithEmail != null && !existingWithEmail.getId().equals(id)) {
            throw new DadosDuplicadosException("Email já cadastrado por outro orientador");
        }

        // Verifica se a matrícula já está em uso por outro orientador
        Advisor existingWithRegistration = repository.findByRegistration(advisor.getRegistration());
        if (existingWithRegistration != null && !existingWithRegistration.getId().equals(id)) {
            throw new DadosDuplicadosException("Matrícula já cadastrada por outro orientador");
        }

        // Mantém a senha existente (não atualiza a senha através do update)
        String currentPassword = repository.findById(id).get().getPassword();
        advisor.setPassword(currentPassword);

        return repository.save(advisor).toDTO();
    }

    /**
     * Edita o nome de um orientador específico.
     */
    public AdvisorResponseDTO editName(Long id, String name) {
        Advisor advisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));
        advisor.setName(name);
        return repository.save(advisor).toDTO();
    }

    /**
     * Edita o email de um orientador específico.
     */
    public AdvisorResponseDTO editEmail(Long id, String email) {
        Advisor advisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));

        if (repository.existsByEmail(email) && !repository.findByEmail(email).getId().equals(id)) {
            throw new DadosDuplicadosException("Email já cadastrado por outro orientador");
        }

        advisor.setEmail(email);
        return repository.save(advisor).toDTO();
    }

    /**
     * Edita a matrícula de um orientador específico.
     */
    public AdvisorResponseDTO editRegistration(Long id, Long registration) {
        Advisor advisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));

        if (repository.existsByRegistration(registration) &&
                !repository.findByRegistration(registration).getId().equals(id)) {
            throw new DadosDuplicadosException("Matrícula já cadastrada por outro orientador");
        }

        advisor.setRegistration(registration);
        return repository.save(advisor).toDTO();
    }

    /**
     * Edita a senha de um orientador específico.
     */
    public AdvisorResponseDTO editPassword(Long id, String password) {
        Advisor advisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));

        // Criptografa a nova senha antes de salvar
        advisor.setPassword(passwordEncoder.encode(password));
        return repository.save(advisor).toDTO();
    }

    /**
     * Edita a imagem de um orientador específico.
     */
    public AdvisorResponseDTO editImage(Long id, String image) {
        Advisor advisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));
        advisor.setImage(image);
        return repository.save(advisor).toDTO();
    }

    /**
     * Lista todos os orientadores com paginação.
     */
    public Page<AdvisorResponseDTO> findAllAdvisors(Pageable pageable) {
        Page<Advisor> advisors = repository.findAll(pageable);
        if (advisors.isEmpty()) {
            throw new NaoEncontradoException("Nenhum orientador encontrado");
        }
        return advisors.map(Advisor::toDTO);
    }

    /**
     * Busca um orientador pelo seu ID.
     */
    public AdvisorResponseDTO findAdvisorById(Long id) {
        return repository.findById(id)
                .map(Advisor::toDTO)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));
    }

    /**
     * Busca um orientador pelo seu email.
     */
    public AdvisorResponseDTO findAdvisorByEmail(String email) {
        return repository.findByEmail(email)
                .map(Advisor::toDTO)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));
    }

    /**
     * Filtra orientadores baseados em um termo de busca (nome, email ou matrícula).
     */
    public Page<AdvisorResponseDTO> filterAdvisors(String termo, Pageable pageable) {
        return repository.findAll(AdvisorSpecification.advisorFilter(termo), pageable)
                .map(Advisor::toDTO);
    }

    /**
     * Deleta um orientador pelo seu ID.
     */
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NaoEncontradoException("Orientador não encontrado");
        }
        repository.deleteById(id);
    }
}