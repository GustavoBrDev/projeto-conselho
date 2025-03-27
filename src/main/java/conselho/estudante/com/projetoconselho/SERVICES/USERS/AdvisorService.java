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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


@Service
@AllArgsConstructor
public class AdvisorService {


    private final AdvisorRepository repository;
    private final PasswordEncoder passwordEncoder;


    // Regex para validação de email
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);


    /**
     * Cria um novo orientador com validações
     */
    public AdvisorResponseDTO create(AdvisorRequestDTO advisorRequestDTO) {
        validateAdvisorRequest(advisorRequestDTO);


        Advisor advisor = advisorRequestDTO.convert();


        if (repository.existsByEmail(advisor.getEmail())) {
            throw new DadosDuplicadosException("Email já cadastrado");
        }


        if (repository.existsByRegistration(advisor.getRegister())) {
            throw new DadosDuplicadosException("Matrícula já cadastrada");
        }


        // Valida formato do email
        if (!isValidEmail(advisor.getEmail())) {
            throw new IllegalArgumentException("Formato de email inválido");
        }


        // Criptografa a senha antes de salvar
        advisor.setPassword(passwordEncoder.encode(advisor.getPassword()));
        advisor.setCreatedAt(new Date());


        return convertToDTO(repository.save(advisor));
    }


    /**
     * Atualiza um orientador existente
     */
    public AdvisorResponseDTO update(Long id, AdvisorRequestDTO advisorRequestDTO) {
        validateAdvisorRequest(advisorRequestDTO);


        Advisor existingAdvisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));


        Advisor advisor = advisorRequestDTO.convert();
        advisor.setId(id);


        // Verifica se o email já está em uso por outro orientador
        repository.findByEmail(advisor.getEmail())
                .ifPresent(a -> {
                    if (!a.getId().equals(id)) {
                        throw new DadosDuplicadosException("Email já cadastrado por outro orientador");
                    }
                });


        // Verifica se a matrícula já está em uso por outro orientador
        repository.findByRegistration(advisor.getRegister())
                .ifPresent(a -> {
                    if (!a.getId().equals(id)) {
                        throw new DadosDuplicadosException("Matrícula já cadastrada por outro orientador");
                    }
                });


        // Valida formato do email
        if (!isValidEmail(advisor.getEmail())) {
            throw new IllegalArgumentException("Formato de email inválido");
        }


        // Mantém dados que não devem ser alterados no update
        advisor.setPassword(existingAdvisor.getPassword());
        advisor.setCreatedAt(existingAdvisor.getCreatedAt());


        return convertToDTO(repository.save(advisor));
    }


    /**
     * Métodos PATCH para edições específicas
     */
    public AdvisorResponseDTO editName(Long id, String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }


        Advisor advisor = getAdvisorById(id);
        advisor.setName(name);
        return convertToDTO(repository.save(advisor));
    }


    public AdvisorResponseDTO editEmail(Long id, String email) {
        if (StringUtils.isBlank(email) || !isValidEmail(email)) {
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
        return convertToDTO(repository.save(advisor));
    }


    public AdvisorResponseDTO editRegistration(Long id, Long registration) {
        if (registration == null) {
            throw new IllegalArgumentException("Matrícula não pode ser nula");
        }


        Advisor advisor = getAdvisorById(id);


        repository.findByRegistration(registration)
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
        advisor.setPassword(passwordEncoder.encode(password));
        return convertToDTO(repository.save(advisor));
    }


    public AdvisorResponseDTO editImage(Long id, String image) {
        Advisor advisor = getAdvisorById(id);
        advisor.setImage(image);
        return convertToDTO(repository.save(advisor));
    }


    /**
     * Métodos GET para consultas
     */
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


    /**
     * Métodos de busca com filtros
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
     * Método DELETE
     */
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NaoEncontradoException("Orientador não encontrado");
        }
        repository.deleteById(id);
    }


    /**
     * Métodos auxiliares
     */
    private Advisor getAdvisorById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));
    }


    private boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }


    private void validateAdvisorRequest(AdvisorRequestDTO request) {
        Assert.notNull(request, "Dados do orientador não podem ser nulos");
        Assert.hasText(request.getName(), "Nome é obrigatório");
        Assert.hasText(request.getEmail(), "Email é obrigatório");
        Assert.hasText(request.getPassword(), "Senha é obrigatória");
        Assert.notNull(request.getRegister(), "Matrícula é obrigatória");


        if (!isValidEmail(request.getEmail())) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
    }


    private AdvisorResponseDTO convertToDTO(Advisor advisor) {
        return new AdvisorResponseDTO(
                advisor.getId(),
                advisor.getName(),
                advisor.getImage(),
                advisor.getEmail(),
                advisor.getRegister()
        );
    }
}
