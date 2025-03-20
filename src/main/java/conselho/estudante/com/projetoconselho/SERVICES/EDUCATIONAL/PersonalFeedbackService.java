package conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL.PersonalFeedbackRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.PersonalFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.PersonalFeedback;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL.CouncilRepository;
import conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL.PersonalFeedbackRepository;
import conselho.estudante.com.projetoconselho.REPOSITORIES.USERS.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pela gestão dos feedbacks pessoais ({@link PersonalFeedback}).
 * Contém operações CRUD e manipulação de feedbacks por conselho.
 * @author Camilly Chelest
 * @since 20/03/2025
 */
@Service
@AllArgsConstructor
public class PersonalFeedbackService {

    private final PersonalFeedbackRepository repository;
    private final CouncilRepository councilRepository;
    private final StudentRepository studentRepository;

    /**
     * Cria um novo feedback pessoal.
     * @param requestDTO Dados do feedback
     * @return Feedback criado
     */
    public PersonalFeedbackResponseDTO create(PersonalFeedbackRequestDTO requestDTO) {
        Council council = councilRepository.findById(requestDTO.councilId())
                .orElseThrow(() -> new NaoEncontradoException("Conselho não encontrado"));

        Student student = studentRepository.findById(requestDTO.studentId())
                .orElseThrow(() -> new NaoEncontradoException("Estudante não encontrado"));

        PersonalFeedback feedback = requestDTO.convert(council, student);
        return repository.save(feedback).convert();
    }

    /**
     * Atualiza um feedback existente.
     * @param id ID do feedback
     * @param requestDTO Dados atualizados
     * @return Feedback atualizado
     */
    public PersonalFeedbackResponseDTO update(Long id, PersonalFeedbackRequestDTO requestDTO) {
        PersonalFeedback feedback = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Feedback não encontrado"));

        feedback.setCreatedAt(requestDTO.createdAt());
        feedback.setText(requestDTO.text());

        return repository.save(feedback).convert();
    }

    /**
     * Edita os textos de um feedback específico.
     * @param id ID do feedback
     * @param text Novo texto do feedback
     * @return Feedback atualizado
     */
    public PersonalFeedbackResponseDTO editText(Long id, String text) {
        PersonalFeedback feedback = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Feedback não encontrado"));

        if (text != null) feedback.setText(text);

        return repository.save(feedback).convert();
    }

    /**
     * Lista todos os feedbacks com suporte a paginação.
     * @param pageable Configuração de paginação
     * @return Página de feedbacks
     */
    public Page<PersonalFeedbackResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(PersonalFeedback::convert);
    }

    /**
     * Lista todos os feedbacks de um conselho específico com suporte a paginação.
     * @param councilId ID do conselho
     * @param pageable Configuração de paginação
     * @return Página de feedbacks do conselho
     */
    public Page<PersonalFeedbackResponseDTO> findByCouncil(Long councilId, Pageable pageable) {
        return repository.findByCouncilId(councilId, pageable).map(PersonalFeedback::convert);
    }

    /**
     * Busca um feedback pelo ID.
     * @param id ID do feedback
     * @return Feedback encontrado
     */
    public PersonalFeedbackResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(PersonalFeedback::convert)
                .orElseThrow(() -> new NaoEncontradoException("Feedback não encontrado"));
    }

    /**
     * Deleta um feedback pelo ID.
     * @param id ID do feedback
     */
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NaoEncontradoException("Feedback não encontrado");
        }
        repository.deleteById(id);
    }
}
