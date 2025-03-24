package SERVICES.USERS;

import MODELS.ENTITY.DTO.REQUEST.AdvisorRequestDTO;
import MODELS.ENTITY.DTO.RESPONSE.AdvisorResponseDTO;
import MODELS.ENTITY.EXCEPTIONS.DadosDuplicadosException;
import MODELS.ENTITY.EXCEPTIONS.NaoEncontradoException;
import MODELS.ENTITY.USERS.Advisor;
import REPOSITORIES.USERS.AdvisorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        return convertToDTO(repository.save(advisor));
    }

    /**
     * Atualiza um orientador existente.
     */
    public AdvisorResponseDTO update(Long id, AdvisorRequestDTO advisorRequestDTO) {
        Advisor existingAdvisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));

        Advisor advisor = advisorRequestDTO.convert();
        advisor.setId(id);

        // Verifica se o email já está em uso por outro orientador
        Optional<Advisor> advisorWithEmail = repository.findByEmail(advisor.getEmail());
        if (advisorWithEmail.isPresent() && !advisorWithEmail.get().getId().equals(id)) {
            throw new DadosDuplicadosException("Email já cadastrado por outro orientador");
        }

        // Verifica se a matrícula já está em uso por outro orientador
        Optional<Advisor> advisorWithRegistration = repository.findByRegistration(advisor.getRegistration());
        if (advisorWithRegistration.isPresent() && !advisorWithRegistration.get().getId().equals(id)) {
            throw new DadosDuplicadosException("Matrícula já cadastrada por outro orientador");
        }

        // Mantém a senha existente (não atualiza a senha através do update)
        advisor.setPassword(existingAdvisor.getPassword());

        return convertToDTO(repository.save(advisor));
    }

    /**
     * Edita o nome de um orientador específico.
     */
    public AdvisorResponseDTO editName(Long id, String name) {
        Advisor advisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));
        advisor.setName(name);
        return convertToDTO(repository.save(advisor));
    }

    /**
     * Edita o email de um orientador específico.
     */
    public AdvisorResponseDTO editEmail(Long id, String email) {
        Advisor advisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));

        Optional<Advisor> advisorWithEmail = repository.findByEmail(email);
        if (advisorWithEmail.isPresent() && !advisorWithEmail.get().getId().equals(id)) {
            throw new DadosDuplicadosException("Email já cadastrado por outro orientador");
        }

        advisor.setEmail(email);
        return convertToDTO(repository.save(advisor));
    }

    /**
     * Edita a matrícula de um orientador específico.
     */
    public AdvisorResponseDTO editRegistration(Long id, Long registration) {
        Advisor advisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));

        Optional<Advisor> advisorWithRegistration = repository.findByRegistration(registration);
        if (advisorWithRegistration.isPresent() && !advisorWithRegistration.get().getId().equals(id)) {
            throw new DadosDuplicadosException("Matrícula já cadastrada por outro orientador");
        }

        advisor.setRegistration(registration);
        return convertToDTO(repository.save(advisor));
    }

    /**
     * Edita a senha de um orientador específico.
     */
    public AdvisorResponseDTO editPassword(Long id, String password) {
        Advisor advisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));

        // Criptografa a nova senha antes de salvar
        advisor.setPassword(passwordEncoder.encode(password));
        return convertToDTO(repository.save(advisor));
    }

    /**
     * Edita a imagem de um orientador específico.
     */
    public AdvisorResponseDTO editImage(Long id, String image) {
        Advisor advisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));
        advisor.setImage(image);
        return convertToDTO(repository.save(advisor));
    }

    /**
     * Lista todos os orientadores com paginação.
     */
    public Page<AdvisorResponseDTO> findAllAdvisors(Pageable pageable) {
        Page<Advisor> advisors = repository.findAll(pageable);
        if (advisors.isEmpty()) {
            throw new NaoEncontradoException("Nenhum orientador encontrado");
        }
        return advisors.map(this::convertToDTO);
    }

    /**
     * Busca um orientador pelo seu ID.
     */
    public AdvisorResponseDTO findAdvisorById(Long id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));
    }

    /**
     * Busca um orientador pelo seu email.
     */
    public AdvisorResponseDTO findAdvisorByEmail(String email) {
        return repository.findByEmail(email)
                .map(this::convertToDTO)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));
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

    /**
     * Converte a entidade Advisor para DTO
     */
    private AdvisorResponseDTO convertToDTO(Advisor advisor) {
        return new AdvisorResponseDTO(
                advisor.getId(),
                advisor.getName(),
                advisor.getImage(),
                advisor.getEmail(),
                advisor.getRegistration()
        );
    }
}