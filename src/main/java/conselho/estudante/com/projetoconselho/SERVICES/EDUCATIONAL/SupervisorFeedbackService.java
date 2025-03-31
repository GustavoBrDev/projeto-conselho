package conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL.SupervisorFeedbackRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL.SupervisorFeedbackRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.SupervisorFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.SupervisorFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.SupervisorFeedback;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.SupervisorFeeback;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.ChangeItem;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.EditableItem;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Supervisor;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;


import conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL.SupervisorFeedbackRepository;
import conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL.COUNCIL.CouncilService;
import conselho.estudante.com.projetoconselho.SERVICES.LOGS.FeedbackLogsService;
import conselho.estudante.com.projetoconselho.SERVICES.USERS.SupervisorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Serviço responsável pela gestão dos feedbacks de supervisores ({@link SupervisorFeedback}).
 * Contém operações CRUD e manipulação de feedbacks por conselho e supervisor.
 * @author Camilly Chelest
 * @since 19/03/2025
 * 
 * Atualizado em 31/03/2025
 * Conexão com o FeedbackLogsService para gerar logs
 * Melhoria no tratamento de erros
 * @author Gustavo Stinghen
 * @see FeedbackLogsService
 */
@Service
@AllArgsConstructor
public class SupervisorFeedbackService {

    private SupervisorFeedbackRepository repository;
    private FeedbackLogsService logsService;
    /**
     * Cria um novo feedback de supervisor.
     * @param requestDTO Dados do feedback
     * @param actor Usuário que criou o feedback
     * @return Feedback criado
     */
    public SupervisorFeedbackResponseDTO create(SupervisorFeedbackRequestDTO requestDTO, User actor) {
        
        try {
            SupervisorFeedback supervisorFeedback = requestDTO.convert();
            supervisorFeedback.setCreatedAt(new Date());
            logsService.create(actor, supervisorFeedback, "create");
            return repository.save(supervisorFeedback).convert();
        } catch (Exception e) {
            throw new NaoEncontradoException("Supervisor nao encontrado");
        }
    }


    /**
     * Atualiza um feedback existente.
     * @param id ID do feedback
     * @param requestDTO Dados atualizados
     * @param actor Usuário que atualizou o feedback
     * @return Feedback atualizado
     */
    public SupervisorFeedbackResponseDTO update(Long id, SupervisorFeedbackRequestDTO requestDTO, User actor) {

        try {
            SupervisorFeedback feedback = repository.findById(id)
                    .orElseThrow(() -> new NaoEncontradoException("Feedback nao encontrado"));
            feedback.setStrengthsText(requestDTO.strengthsText());
            feedback.setWeaknessesText(requestDTO.weaknessesText());
            feedback.setSuggestionsText(requestDTO.suggestionsText());
            logsService.create(actor, feedback, getEditableItems(feedback, requestDTO.convert()), "update");
            return repository.save(feedback).convert();
        } catch (Exception e) {
            throw new NaoEncontradoException("Feedback nao encontrado");
        }
    }

    /**
     * Método auxilair para obter os itens editáveis de um feedback.
     * @param oldFeedback o feedback antigo
     * @param newFeedback o feedback novo
     * @return uma lista de itens editáveis
     */
    private List<EditableItem> getEditableItems(SupervisorFeedback oldFeedback, SupervisorFeedback newFeedback) {

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
    public SupervisorFeedbackResponseDTO editTexts(Long id, String strengths, String weaknesses, String suggestions, User actor) {
        SupervisorFeedback feedback = repository.findById(id)
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
    private SupervisorFeedbackResponseDTO editStrenghtsText(Long id, String strengths, User actor) {
        SupervisorFeedback feedback = repository.findById(id)
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
    private SupervisorFeedbackResponseDTO editWeaknessesText(Long id, String weaknesses, User actor) {
        SupervisorFeedback feedback = repository.findById(id)
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
    private SupervisorFeedbackResponseDTO editSuggestionsText(Long id, String suggestions, User actor) {
        SupervisorFeedback feedback = repository.findById(id)
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
    public Page<SupervisorFeedbackResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(SupervisorFeedback::convert);
    }

    /**
     * Lista todos os feedbacks de um conselho específico com suporte a paginação.
     * @param councilId ID do conselho
     * @param pageable Configuração de paginação
     * @return Página de feedbacks do conselho
     */
    public Page<SupervisorFeedbackResponseDTO> findByCouncil(Long councilId, Pageable pageable) {
        return repository.findByCouncilId(councilId, pageable).map(SupervisorFeedback::convert);
    }

    /**
     * Busca um feedback pelo ID.
     * @param id ID do feedback
     * @return Feedback encontrado
     */
    public SupervisorFeedbackResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(SupervisorFeedback::convert)
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
