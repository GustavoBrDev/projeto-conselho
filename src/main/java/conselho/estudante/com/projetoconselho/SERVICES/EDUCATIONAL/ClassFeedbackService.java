package conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL.ClassFeedbackRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.ClassFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.ClassFeedback;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL.ClassFeedbackRepository;
import conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION.ClasseService;
import conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL.COUNCIL.CouncilService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pela gestão dos feedbacks de turma ({@link ClassFeedback}).
 * Contém operações CRUD e manipulação de feedbacks por conselho.
 * @author Camilly Chelest
 * @since 20/03/2025
 */
@Service
@AllArgsConstructor
public class ClassFeedbackService {

    private final ClassFeedbackRepository repository;
    private final CouncilService councilService;
    private final ClasseService classeService;

    /**
     * Cria um novo feedback de turma.
     * @param requestDTO Dados do feedback
     * @return Feedback criado
     */
    public ClassFeedbackResponseDTO create(ClassFeedbackRequestDTO requestDTO) {
        /*Council council = councilService.findById(requestDTO.councilId()) //
                .orElseThrow(() -> new NaoEncontradoException("Conselho não encontrado"));

        Classe classe = classeService.findById(requestDTO.classId())
                .orElseThrow(() -> new NaoEncontradoException("Classe não encontrada"));

        ClassFeedback feedback = requestDTO.convert(council, classe);
        return repository.save(feedback).convert();*/
        return null;
    }

    /**
     * Atualiza um feedback existente.
     * @param id ID do feedback
     * @param requestDTO Dados atualizados
     * @return Feedback atualizado
     */
    public ClassFeedbackResponseDTO update(Long id, ClassFeedbackRequestDTO requestDTO) {
        ClassFeedback feedback = repository.findById(id)
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
    public ClassFeedbackResponseDTO editText(Long id, String text) {
        ClassFeedback feedback = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Feedback não encontrado"));

        if (text != null) feedback.setText(text);

        return repository.save(feedback).convert();
    }

    /**
     * Lista todos os feedbacks com suporte a paginação.
     * @param pageable Configuração de paginação
     * @return Página de feedbacks
     */
    public Page<ClassFeedbackResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(ClassFeedback::convert);
    }

    /**
     * Lista todos os feedbacks de um conselho específico com suporte a paginação.
     * @param councilId ID do conselho
     * @param pageable Configuração de paginação
     * @return Página de feedbacks do conselho
     */
    public Page<ClassFeedbackResponseDTO> findByCouncil(Long councilId, Pageable pageable) {
        return repository.findByCouncilId(councilId, pageable).map(ClassFeedback::convert);
    }

    /**
     * Busca um feedback pelo ID.
     * @param id ID do feedback
     * @return Feedback encontrado
     */
    public ClassFeedbackResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(ClassFeedback::convert)
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
