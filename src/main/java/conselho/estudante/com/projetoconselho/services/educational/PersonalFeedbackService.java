package conselho.estudante.com.projetoconselho.services.educational;

import conselho.estudante.com.projetoconselho.models.dto.request.EDUCATIONAL.PersonalFeedbackRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.EDUCATIONAL.PersonalFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.educational.PersonalFeedback;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.educational.PersonalFeedbackRepository;
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

    /**
     * Cria um novo feedback pessoal.
     * @param requestDTO Dados do feedback
     * @return Feedback criado
     */
    public PersonalFeedbackResponseDTO create(PersonalFeedbackRequestDTO requestDTO) {
        return repository.save(requestDTO.convert()).convert();
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
