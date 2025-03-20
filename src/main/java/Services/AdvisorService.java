package Services;

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

@Service
public class AdvisorService {

    @Autowired
    private AdvisorRepository advisorRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Criar novo orientador (POST)
     * @param advisor
     * @return
     */
    public Advisor criarAdvisor(Advisor advisor) {
        validarCamposObrigatorios(advisor);

        /**
         * Verifica se o email ou matrícula já existem
         */
        if (advisorRepository.findByEmail(advisor.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Orientador com o email " + advisor.getEmail() + " já existe.");
        }

        advisor.setPassword(passwordEncoder.encode(advisor.getPassword())); // Criptografa a senha
        advisor.setCreatedAt(new Date()); // Adiciona a data de criação
        return advisorRepository.save(advisor);
    }

    /**
     * Atualizar orientador (PUT)
     * @param id
     * @param novosDados
     * @return
     */
    public Advisor atualizarAdvisor(Long id, Advisor novosDados) {
        Advisor advisor = buscarPorId(id);

        // Atualiza os campos, criptografando a senha novamente
        advisor.setName(novosDados.getName());
        advisor.setEmail(novosDados.getEmail());
        advisor.setPassword(passwordEncoder.encode(novosDados.getPassword()));
        advisor.setImage(novosDados.getImage());
        advisor.setRegister(novosDados.getRegister());
        return advisorRepository.save(advisor);
    }

    /**
     * Editar nome (PATCH)
     * @param id
     * @param novoNome
     * @return
     */
    public Advisor editarNome(Long id, String novoNome) {
        Advisor advisor = buscarPorId(id);
        advisor.setName(novoNome);
        return advisorRepository.save(advisor);
    }

    /**
     * Listar todos os orientadores (GET com paginação)
     * @param pageable
     * @return
     */
    public Page<Advisor> listarTodos(Pageable pageable) {
        return advisorRepository.findAll(pageable);
    }

    /**
     * Buscar orientador por ID (GET)
     * @param id
     * @return
     */
    public Advisor buscarPorId(Long id) {
        return advisorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Orientador com id " + id + " não encontrado."));
    }

    /**
     * Excluir orientador (DELETE)
     * @param id
     */
    public void deletarAdvisor(Long id) {
        Advisor advisor = buscarPorId(id);
        advisorRepository.delete(advisor);
    }

    /**
     * Metodo de validação de campos obrigatórios
     * @param advisor
     */
    private void validarCamposObrigatorios(Advisor advisor) {
        if (!StringUtils.hasText(advisor.getEmail()) || !advisor.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email inválido.");
        }
        if (!StringUtils.hasText(advisor.getPassword()) || advisor.getPassword().length() < 6) {
            throw new IllegalArgumentException("Senha deve conter pelo menos 6 caracteres.");
        }
        if (advisor.getRegister() == null) {
            throw new IllegalArgumentException("Matrícula é obrigatória.");
        }
    }
}