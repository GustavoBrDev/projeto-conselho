package Services;

import MODELS.ENTITY.DTO.REQUEST.AdvisorRequestDTO;
import MODELS.ENTITY.DTO.RESPONSE.AdvisorResponseDTO;
import MODELS.ENTITY.USERS.Advisor;
import REPOSITORIES.USERS.AdvisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;

/**
 * Classe service para o advisor
 * author Alex Zastrow
 */

@Service
public class AdvisorService {

    @Autowired
    private AdvisorRepository advisorRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /*
     * Cria um advisor
     */
    public AdvisorResponseDTO criarAdvisor(AdvisorRequestDTO advisorRequestDTO) {
        validarCamposObrigatorios(advisorRequestDTO);

        if (advisorRepository.findByEmail(advisorRequestDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Orientador com o email " + advisorRequestDTO.getEmail() + " já existe.");
        }

        Advisor advisor = new Advisor();
        advisor.setName(advisorRequestDTO.getName());
        advisor.setEmail(advisorRequestDTO.getEmail());
        advisor.setPassword(passwordEncoder.encode(advisorRequestDTO.getPassword()));
        advisor.setRegister(advisorRequestDTO.getRegister());
        advisor.setImage(advisorRequestDTO.getImage());
        advisor.setCreatedAt(new Date());

        Advisor savedAdvisor = advisorRepository.save(advisor);
        return convertToResponseDTO(savedAdvisor);
    }

    /*
     * Atualiza um advisor
     */
    public AdvisorResponseDTO atualizarAdvisor(Long id, AdvisorRequestDTO advisorRequestDTO) {
        Advisor advisor = buscarPorId(id);

        advisor.setName(advisorRequestDTO.getName());
        advisor.setEmail(advisorRequestDTO.getEmail());
        advisor.setPassword(passwordEncoder.encode(advisorRequestDTO.getPassword()));
        advisor.setRegister(advisorRequestDTO.getRegister());
        advisor.setImage(advisorRequestDTO.getImage());

        Advisor updatedAdvisor = advisorRepository.save(advisor);
        return convertToResponseDTO(updatedAdvisor);
    }

    /*
     * Edita o nome de um advisor
     */
    public AdvisorResponseDTO editarNome(Long id, String novoNome) {
        Advisor advisor = buscarPorId(id);
        advisor.setName(novoNome);
        Advisor updatedAdvisor = advisorRepository.save(advisor);
        return convertToResponseDTO(updatedAdvisor);
    }

    /*
     * Lista todos os advisors
     */
    public Page<AdvisorResponseDTO> listarTodos(Pageable pageable) {
        return advisorRepository.findAll(pageable).map(this::convertToResponseDTO);
    }

    /*
     * Deleta um advisor
     */
    public void deletarAdvisor(Long id) {
        Advisor advisor = buscarPorId(id);
        advisorRepository.delete(advisor);
    }

    /*
     * Valida os campos obrigatorios
     */
    private void validarCamposObrigatorios(AdvisorRequestDTO advisorRequestDTO) {
        if (!StringUtils.hasText(advisorRequestDTO.getEmail()) || !advisorRequestDTO.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email inválido.");
        }
        if (!StringUtils.hasText(advisorRequestDTO.getPassword()) || advisorRequestDTO.getPassword().length() < 6) {
            throw new IllegalArgumentException("Senha deve conter pelo menos 6 caracteres.");
        }
        if (advisorRequestDTO.getRegister() == null) {
            throw new IllegalArgumentException("Matrícula é obrigatória.");
        }
    }

    /*
     * Converte um advisor para um advisorResponseDTO
     */
    private AdvisorResponseDTO convertToResponseDTO(Advisor advisor) {
        AdvisorResponseDTO responseDTO = new AdvisorResponseDTO();
        responseDTO.setId(advisor.getId());
        responseDTO.setName(advisor.getName());
        responseDTO.setEmail(advisor.getEmail());
        responseDTO.setRegister(advisor.getRegister());
        responseDTO.setImage(advisor.getImage());
        responseDTO.setCreatedAt(advisor.getCreatedAt());
        return responseDTO;
    }

    /*
     * Busca um advisor pelo id
     */
    private Advisor buscarPorId(Long id) {
        return advisorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Orientador com id " + id + " não encontrado."));
    }
}