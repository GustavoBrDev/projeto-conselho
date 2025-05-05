package conselho.estudante.com.projetoconselho.services.educational;

import conselho.estudante.com.projetoconselho.models.dto.request.educational.AdvisorFeedbackRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.educational.AdvisorFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.educational.AdvisorFeeback;
import conselho.estudante.com.projetoconselho.models.entity.logs.ChangeItem;
import conselho.estudante.com.projetoconselho.models.entity.logs.EditableItem;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.educational.AdvisorFeedbackRepository;
import conselho.estudante.com.projetoconselho.services.logs.FeedbackLogsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Serviço responsável pela gestão dos feedbacks de orientadores ({@link AdvisorFeeback}).
 * Contém operações CRUD e manipulação de feedbacks por conselho e orientador.
 * @author Camilly Chelest
 * @since 19/03/2025
 *
 * Atualizado em 31/03/2025
 * Conexão com o FeedbackLogsService para gerar logs
 * @author Gustavo Stinghen
 * @see FeedbackLogsService
 */
@Service
@AllArgsConstructor
public class AdvisorFeedbackService {

    private final AdvisorFeedbackRepository repository;
    private final FeedbackLogsService logsService;

    /**
     * Cria um novo feedback de orientador.
     * @param requestDTO Dados do feedback
     * @param actor Usuário que criou o feedback
     * @return Feedback criado
     */
    public AdvisorFeedbackResponseDTO create(AdvisorFeedbackRequestDTO requestDTO, User actor) {
        AdvisorFeeback advisorFeeback = requestDTO.convert();
        advisorFeeback.setCreatedAt(new Date());
        logsService.create(actor, advisorFeeback, "create");
        return repository.save(advisorFeeback).convert();
    }

    /**
     * Atualiza um feedback existente.
     * @param id ID do feedback
     * @param requestDTO Dados atualizados
     * @param actor Usuário que atualizou o feedback
     * @return Feedback atualizado
     */
    public AdvisorFeedbackResponseDTO update(Long id, AdvisorFeedbackRequestDTO requestDTO, User actor) {
        repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Feedback não encontrado"));

        AdvisorFeeback updatedFeedback = AdvisorFeeback.builder()
                .strengthsText(requestDTO.strengthsText())
                .weaknessesText(requestDTO.weaknessesText())
                .suggestionsText(requestDTO.suggestionsText())
                .build();

        logsService.create(actor, updatedFeedback, getEditableItems(repository.findById(id).get(), updatedFeedback), "update");
        return repository.save(updatedFeedback).convert();
    }

    /**
     * Método auxilair para obter os itens editáveis de um feedback.
     * @param oldFeedback o feedback antigo
     * @param newFeedback o feedback novo
     * @return uma lista de itens editáveis
     */
    private List<EditableItem> getEditableItems(AdvisorFeeback oldFeedback, AdvisorFeeback newFeedback) {

        List<EditableItem> changes = new ArrayList<>();

        if (!oldFeedback.getStrengthsText().equals(newFeedback.getStrengthsText())) {
            changes.add(new ChangeItem("strengthsText", oldFeedback.getStrengthsText(), newFeedback.getStrengthsText()));
        }

        if (!oldFeedback.getWeaknessesText().equals(newFeedback.getWeaknessesText())) {
            changes.add(new ChangeItem("weaknessesText", oldFeedback.getWeaknessesText(), newFeedback.getWeaknessesText()));
        }

        if (!oldFeedback.getSuggestionsText().equals(newFeedback.getSuggestionsText())) {
            changes.add(new ChangeItem("suggestionsText", oldFeedback.getSuggestionsText(), newFeedback.getSuggestionsText()));
        }

        return changes;

    }

    /**
     * Edita os textos de um feedback específico.
     * @param id ID do feedback
     * @param strengths Novo texto de pontos fortes
     * @param weaknesses Novo texto de pontos fracos
     * @param suggestions Novas sugestões
     * @param actor Usuário que editou o feedback
     * @return Feedback atualizado
     */
    public AdvisorFeedbackResponseDTO editTexts(Long id, String strengths, String weaknesses, String suggestions, User actor) {
        AdvisorFeeback feedback = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Feedback não encontrado"));

        if (strengths != null) {
            logsService.create(actor, feedback, Collections.singletonList(new ChangeItem("strengthsText", (Object) feedback.getStrengthsText(), (Object) strengths)), "update");
            feedback.setStrengthsText(strengths);
        }

        if (weaknesses != null) {
            logsService.create(actor, feedback, Collections.singletonList(new ChangeItem("weaknessesText", (Object) feedback.getWeaknessesText(), (Object) weaknesses)), "update");
            feedback.setWeaknessesText(weaknesses);
        }
        if (suggestions != null) {
            logsService.create(actor, feedback, Collections.singletonList(new ChangeItem("suggestionsText", (Object) feedback.getSuggestionsText(), (Object) suggestions)), "update");
            feedback.setSuggestionsText(suggestions);
        }

        return repository.save(feedback).convert();
    }

    /**
     * Edita o texto de pontos fortes de um feedback específico.
     * @param id ID do feedback
     * @param strengths Novo texto de pontos fortes
     * @param actor Usuário que editou o feedback
     * @return Feedback atualizado
     * @author Gustavo Stinghen
     * @since 31/03/2025
     */
    private AdvisorFeedbackResponseDTO editStrenghtsText(Long id, String strengths, User actor) {
        AdvisorFeeback feedback = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Feedback não encontrado"));
        logsService.create(actor, feedback, Collections.singletonList(new ChangeItem("strengthsText", (Object) feedback.getStrengthsText(), (Object) strengths)), "update");
        feedback.setStrengthsText(strengths);
        return repository.save(feedback).convert();
    }

    /**
     * Edita o texto de pontos fracos de um feedback específico.
     * @param id ID do feedback
     * @param weaknesses Novo texto de pontos fracos
     * @param actor Usuário que editou o feedback
     * @return Feedback atualizado
     * @author Gustavo Stinghen
     * @since 31/03/2025
     */
    private AdvisorFeedbackResponseDTO editWeaknessesText(Long id, String weaknesses, User actor) {
        AdvisorFeeback feedback = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Feedback não encontrado"));
        logsService.create(actor, feedback, Collections.singletonList(new ChangeItem("weaknessesText", (Object) feedback.getWeaknessesText(), (Object) weaknesses)), "update");
        feedback.setWeaknessesText(weaknesses);
        return repository.save(feedback).convert();
    }

    /**
     * Edita o texto de sugestões de um feedback específico.
     * @param id ID do feedback
     * @param suggestions Novas sugestões
     * @param actor Usuário que editou o feedback
     * @return Feedback atualizado
     * @author Gustavo Stinghen
     * @since 31/03/2025
     */
    private AdvisorFeedbackResponseDTO editSuggestionsText(Long id, String suggestions, User actor) {
        AdvisorFeeback feedback = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Feedback não encontrado"));
        logsService.create(actor, feedback, Collections.singletonList(new ChangeItem("suggestionsText", (Object) feedback.getSuggestionsText(), (Object) suggestions)), "update");
        feedback.setSuggestionsText(suggestions);
        return repository.save(feedback).convert();
    }

    /**
     * Lista todos os feedbacks com suporte a paginação.
     * @param pageable Configuração de paginação
     * @return Página de feedbacks
     */
    public Page<AdvisorFeedbackResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(AdvisorFeeback::convert);
    }

    /**
     * Lista todos os feedbacks de um conselho específico com suporte a paginação.
     * @param councilId ID do conselho
     * @param pageable Configuração de paginação
     * @return Página de feedbacks do conselho
     */
    public Page<AdvisorFeedbackResponseDTO> findByCouncil(Long councilId, Pageable pageable) {
        return repository.findByCouncilId(councilId, pageable).map(AdvisorFeeback::convert);
    }

    /**
     * Busca um feedback pelo ID.
     * @param id ID do feedback
     * @return Feedback encontrado
     */
    public AdvisorFeedbackResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(AdvisorFeeback::convert)
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
