package conselho.estudante.com.projetoconselho.services.educational;

import conselho.estudante.com.projetoconselho.models.dto.request.educational.ClassFeedbackRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.educational.ClassFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.educational.ClassFeedback;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.educational.ClassFeedbackRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    /**
     * Cria um novo feedback de turma.
     * @param requestDTO Dados do feedback
     * @return Feedback criado
     */
    public ClassFeedbackResponseDTO create(ClassFeedbackRequestDTO requestDTO) {
        ClassFeedback classFeedback = requestDTO.convert();
        classFeedback.setCreatedAt(new Date());

        classFeedback = repository.save(classFeedback);

        return classFeedback.convert();
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
