package conselho.estudante.com.projetoconselho.SERVICES.USERS;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS.AdvisorRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.AdvisorResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Advisor;
import conselho.estudante.com.projetoconselho.REPOSITORIES.USERS.AdvisorRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import jakarta.persistence.criteria.Predicate;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AdvisorService {

    private final AdvisorRepository repository;

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
    public AdvisorResponseDTO editName(Long id, String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        Advisor advisor = getAdvisorById(id);
        advisor.setName(name);
        return convertToDTO(repository.save(advisor));
    }

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

    public AdvisorResponseDTO editPassword(Long id, String password) {
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }
        
        Advisor advisor = getAdvisorById(id);
        return convertToDTO(repository.save(advisor));
    }

    public AdvisorResponseDTO editImage(Long id, String image) {
        Advisor advisor = getAdvisorById(id);
        advisor.setImage(image);
        return convertToDTO(repository.save(advisor));
    }

    /**
     * Método de edição de senha (interno)
     *
     * @param advisor  orientador
     * @param password nova senha
     * @return
     * @author Gustavo Stinghen
     * @since 31/03/2025
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

    public Page<AdvisorResponseDTO> findAllAdvisors(Pageable pageable) {
        Page<Advisor> advisors = repository.findAll(pageable);
        if (advisors.isEmpty()) {
            throw new NaoEncontradoException("Nenhum orientador encontrado");
        }
        return advisors.map(this::convertToDTO);
    }

    public AdvisorResponseDTO findAdvisorById(Long id) {
        return convertToDTO(getAdvisorById(id));
    }

    public AdvisorResponseDTO findAdvisorByEmail(String email) {
        return repository.findByEmail(email)
                .map(this::convertToDTO)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));
    }

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

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NaoEncontradoException("Orientador não encontrado");
        }
        repository.deleteById(id);
    }
    public Advisor getAdvisorById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));
    }

    private void validateAdvisorRequest(AdvisorRequestDTO request) {
        Assert.notNull(request, "Dados do orientador não podem ser nulos");
        Assert.hasText(request.name(), "Nome é obrigatório");
        Assert.hasText(request.email(), "Email é obrigatório");
        Assert.hasText(request.password(), "Senha é obrigatória");
        Assert.notNull(request.register(), "Matrícula é obrigatória");
    }

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
