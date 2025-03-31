package conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL.ItemFeedbackRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.ItemFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.ItemFeedback;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL.ItemFeedbackRepository;
import conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL.COUNCIL.CouncilService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pela gestão dos feedbacks de itens ({@link ItemFeedback}).
 * Contém operações CRUD e manipulação de feedbacks por conselho.
 * @author Camilly Chelest
 * @since 19/03/2025
 */
@Service
@AllArgsConstructor
public class ItemFeedbackService {

    private final ItemFeedbackRepository repository;
    private final CouncilService councilService;

    /**
     * Cria um novo feedback de item.
     * @param requestDTO Dados do feedback
     * @return Feedback criado
     */
    public ItemFeedbackResponseDTO create(ItemFeedbackRequestDTO requestDTO) {
        return repository.save(requestDTO.convert()).convert();
    }



    /**
     * Atualiza um feedback existente.
     * @param id ID do feedback
     * @param requestDTO Dados atualizados
     * @return Feedback atualizado
     */
    public ItemFeedbackResponseDTO update(Long id, ItemFeedbackRequestDTO requestDTO) {
        ItemFeedback feedback = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Feedback não encontrado"));

        feedback.setCreatedAt(requestDTO.createdAt());
        feedback.setText(requestDTO.text());
        feedback.setItem(requestDTO.item());

        return repository.save(feedback).convert();
    }

    /**
     * Edita os textos de um feedback específico.
     * @param id ID do feedback
     * @param text Novo texto do feedback
     * @param item Novo item relacionado ao feedback
     * @return Feedback atualizado
     */
    public ItemFeedbackResponseDTO editTexts(Long id, String text, String item) {
        ItemFeedback feedback = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Feedback não encontrado"));

        if (text != null) feedback.setText(text);
        if (item != null) feedback.setItem(item);

        return repository.save(feedback).convert();
    }

    /**
     * Lista todos os feedbacks com suporte a paginação.
     * @param pageable Configuração de paginação
     * @return Página de feedbacks
     */
    public Page<ItemFeedbackResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(ItemFeedback::convert);
    }

    /**
     * Lista todos os feedbacks de um conselho específico com suporte a paginação.
     * @param councilId ID do conselho
     * @param pageable Configuração de paginação
     * @return Página de feedbacks do conselho
     */
    public Page<ItemFeedbackResponseDTO> findByCouncil(Long councilId, Pageable pageable) {
        return repository.findByCouncilId(councilId, pageable).map(ItemFeedback::convert);
    }

    /**
     * Busca um feedback pelo ID.
     * @param id ID do feedback
     * @return Feedback encontrado
     */
    public ItemFeedbackResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(ItemFeedback::convert)
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
