package conselho.estudante.com.projetoconselho.services.educational;

import conselho.estudante.com.projetoconselho.models.dto.request.educational.ItemFeedbackRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.educational.ItemFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.educational.ItemFeedback;
import conselho.estudante.com.projetoconselho.models.entity.logs.ChangeItem;
import conselho.estudante.com.projetoconselho.models.entity.logs.EditableItem;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.educational.ItemFeedbackRepository;
import conselho.estudante.com.projetoconselho.services.logs.FeedbackLogsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Serviço responsável pela gestão dos feedbacks de itens ({@link ItemFeedback}).
 * Contém operações CRUD e manipulação de feedbacks por conselho.
 * @author Camilly Chelest
 * @since 19/03/2025
 *
 * Atualizado em 31/03/2025
 * Conexão com o FeedbackLogsService para gerar logs
 * Aprimorado o tratamento de erros
 * @author Gustavo Stinghen
 * @see FeedbackLogsService
 */
@Service
@AllArgsConstructor
public class ItemFeedbackService {

    private final ItemFeedbackRepository repository;
    private final FeedbackLogsService logsService;

    /**
     * Cria um novo feedback de item.
     * @param requestDTO Dados do feedback
     * @param actor Usuário que criou o feedback
     * @return Feedback criado
     */
    public ItemFeedbackResponseDTO create(ItemFeedbackRequestDTO requestDTO, User actor) {

        try {
            ItemFeedback feedback = requestDTO.convert();
            feedback.setCreatedAt(new java.util.Date());
            logsService.create(actor, feedback, "create");
            return repository.save(feedback).convert();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar o feedback: " + e.getMessage());
        }
    }

    /**
     * Atualiza um feedback existente.
     * @param id ID do feedback
     * @param requestDTO Dados atualizados
     * @param actor Usuário que atualizou o feedback
     * @return Feedback atualizado
     */
    public ItemFeedbackResponseDTO update(Long id, ItemFeedbackRequestDTO requestDTO, User actor) {

        try {
            ItemFeedback feedback = repository.findById(id)
                    .orElseThrow(() -> new NaoEncontradoException("Feedback nao encontrado"));
            feedback.setItem(requestDTO.item());
            feedback.setText(requestDTO.text());
            logsService.create(actor, feedback, getEditableItems(feedback, requestDTO.convert()), "update");
            return repository.save(feedback).convert();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o feedback: " + e.getMessage());
        }
    }

    /**
     * Metodo auxilair para obter os itens editáveis de um feedback.
     * @param oldFeedback o feedback antigo
     * @param newFeedback o feedback novo
     * @return uma lista de itens editáveis
     */
    private List<EditableItem> getEditableItems(ItemFeedback oldFeedback, ItemFeedback newFeedback) {

        List<EditableItem> changes = new ArrayList<>();

        if (!oldFeedback.getText().equals(newFeedback.getText())) {
            changes.add(new ChangeItem("text", oldFeedback.getText(), newFeedback.getText()));
        }

        if (!oldFeedback.getItem().equals(newFeedback.getItem())) {
            changes.add(new ChangeItem("item", oldFeedback.getItem(), newFeedback.getItem()));
        }

        return changes;
    }

    /**
     * Edita os textos de um feedback específico.
     * @param id ID do feedback
     * @param text Novo texto do feedback
     * @param item Novo item relacionado ao feedback
     * @param actor Usuário que atualizou o feedback
     * @return Feedback atualizado
     */
    public ItemFeedbackResponseDTO editTexts(Long id, String text, String item, User actor) {
        ItemFeedback feedback = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Feedback não encontrado"));

        if (text != null) {
            logsService.create(actor, feedback, Collections.singletonList(new ChangeItem("text", (Object) feedback.getText(), (Object) text)), "update");
            feedback.setText(text);
        }
        if (item != null) {
            logsService.create(actor, feedback, Collections.singletonList(new ChangeItem("item", (Object) feedback.getItem(), (Object) item)), "update");
            feedback.setItem(item);
        }

        return repository.save(feedback).convert();
    }

    /**
     * Edita os textos de um feedback específico.
     * @param id ID do feedback
     * @param text Novo texto do feedback
     * @param actor Usuário que atualizou o feedback
     * @return Feedback atualizado
     * @author Gustavo Stinghen
     * @since 31/03/2025
     */
    public ItemFeedbackResponseDTO editText(Long id, String text, User actor) {

        ItemFeedback feedback = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Feedback nao encontrado"));

        if (text != null) {
            logsService.create(actor, feedback, Collections.singletonList(new ChangeItem("text", (Object) feedback.getText(), (Object) text)), "update");
            feedback.setText(text);
        }

        return repository.save(feedback).convert();
    }

    /**
     * Edita os textos de um feedback específico.
     * @param id ID do feedback
     * @param item Novo item relacionado ao feedback
     * @param actor Usuário que atualizou o feedback
     * @return Feedback atualizado
     * @author Gustavo Stinghen
     * @since 31/03/2025
     */
    public ItemFeedbackResponseDTO editItem(Long id, String item, User actor) {

        ItemFeedback feedback = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Feedback nao encontrado"));

        if (item != null) {
            logsService.create(actor, feedback, Collections.singletonList(new ChangeItem("item", (Object) feedback.getItem(), (Object) item)), "update");
            feedback.setItem(item);
        }

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
     * @param actor Usuário que deletou o feedback
     */
    public void delete(Long id, User actor) {
        if (!repository.existsById(id)) {
            throw new NaoEncontradoException("Feedback não encontrado");
        }

        logsService.create(actor, repository.findById(id).get(), "delete");
        repository.deleteById(id);
    }
}
