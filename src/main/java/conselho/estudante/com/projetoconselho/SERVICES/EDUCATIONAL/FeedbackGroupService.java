package conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL.FeedbackGroupRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.FeedbackGroupResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.FeedbackGroup;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL.FeedbackGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Serviço responsável pela gestão dos grupos de feedbacks ({@link FeedbackGroup}).
 * Contém operações CRUD e métodos para manipulação de feedbacks por conselho.
 * @author Camilly Chelest
 * @since 19/03/2025
 */
@Service
@AllArgsConstructor
public class FeedbackGroupService {

    private final FeedbackGroupRepository repository;

    /**
     * Cria um novo grupo de feedbacks.
     *
     * @param requestDTO Dados para criação do grupo
     * @return Grupo de feedbacks criado
     */
    public FeedbackGroupResponseDTO create(FeedbackGroupRequestDTO requestDTO) {
        FeedbackGroup feedbackGroup = new FeedbackGroup();
        feedbackGroup.setDate(requestDTO.date());
        feedbackGroup.setPersonalFeedbackId(requestDTO.personalFeedbackId());
        feedbackGroup.setClassFeedbackId(requestDTO.classFeedbackId());

        return repository.save(feedbackGroup).convert();
    }

    /**
     * Atualiza um grupo de feedbacks existente.
     *
     * @param id         ID do grupo a ser atualizado
     * @param requestDTO Dados atualizados
     * @return Grupo de feedbacks atualizado
     */
    public FeedbackGroupResponseDTO update(Long id, FeedbackGroupRequestDTO requestDTO) {
        FeedbackGroup feedbackGroup = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Grupo de feedbacks não encontrado"));

        feedbackGroup.setDate(requestDTO.date());
        feedbackGroup.setPersonalFeedbackId(requestDTO.personalFeedbackId());
        feedbackGroup.setClassFeedbackId(requestDTO.classFeedbackId());

        return repository.save(feedbackGroup).convert();
    }

    /**
     * Edita a data de um grupo de feedbacks.
     *
     * @param id      ID do grupo
     * @param newDate Nova data do feedback
     * @return Grupo de feedbacks atualizado
     */
    public FeedbackGroupResponseDTO editDate(Long id, Date newDate) {
        FeedbackGroup feedbackGroup = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Grupo de feedbacks não encontrado"));

        feedbackGroup.setDate(newDate);
        return repository.save(feedbackGroup).convert();
    }

    /**
     * Lista todos os grupos de feedbacks com paginação.
     *
     * @param pageable Configuração da paginação
     * @return Página contendo os grupos de feedbacks
     */
    public Page<FeedbackGroupResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(FeedbackGroup::convert);
    }


    /**
     * Lista todos os grupos de feedbacks de um conselho específico com paginação.
     * @param councilId ID do conselho
     * @param pageable Configuração da paginação
     * @return Página contendo os grupos de feedbacks do conselho
     */
    public Page<FeedbackGroupResponseDTO> findByCouncil(Long councilId, Pageable pageable) {
        return repository.findByClassFeedback_CouncilId(councilId, pageable).map(FeedbackGroup::convert);
    }

    /**
     * Busca um grupo de feedbacks pelo ID.
     * @param id ID do grupo
     * @return Grupo de feedbacks encontrado
     */
    public FeedbackGroupResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(FeedbackGroup::convert)
                .orElseThrow(() -> new NaoEncontradoException("Grupo de feedbacks não encontrado"));
    }

    /**
     * Deleta um grupo de feedbacks pelo ID.
     * @param id ID do grupo a ser deletado
     */
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NaoEncontradoException("Grupo de feedbacks não encontrado");
        }
        repository.deleteById(id);
    }
}