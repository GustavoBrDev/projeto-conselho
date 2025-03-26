package conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL.AdvisorFeedbackRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.AdvisorFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.AdvisorFeeback;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Feedback;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.FeedbackLogs;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Advisor;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL.AdvisorFeedbackRepository;
import conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL.CouncilRepository;
import conselho.estudante.com.projetoconselho.REPOSITORIES.USERS.AdvisorRepository;
import conselho.estudante.com.projetoconselho.SERVICES.LOGS.FeedbackLogsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pela gestão dos feedbacks de orientadores ({@link AdvisorFeeback}).
 * Contém operações CRUD e manipulação de feedbacks por conselho e orientador.
 * @author Camilly Chelest
 * @since 19/03/2025
 *
 * Atualizado em 21/03/2025
 * Conexão com o FeedbackLogsService para gerar logs
 * @author Gustavo Stinghen
 * @see FeedbackLogsService
 */
@Service
@AllArgsConstructor
public class AdvisorFeedbackService {

    private final AdvisorFeedbackRepository repository;
    private final CouncilService councilService;
    private final AdvisorService advisorService;

    /**
     * Cria um novo feedback de orientador.
     * @param requestDTO Dados do feedback
     * @return Feedback criado
     */
    public AdvisorFeedbackResponseDTO create(AdvisorFeedbackRequestDTO requestDTO) {
        Council council = councilService.findById(requestDTO.councilId())
                .orElseThrow(() -> new NaoEncontradoException("Conselho não encontrado"));

        Advisor advisor = advisorService.findById(requestDTO.advisorId())
                .orElseThrow(() -> new NaoEncontradoException("Orientador não encontrado"));

        AdvisorFeeback feedback = requestDTO.convert(council, advisor);
        logsService.create(advisor, (Feedback) feedback, "create");
        return repository.save(feedback).convert();
    }

    /**
     * Atualiza um feedback existente.
     * @param id ID do feedback
     * @param requestDTO Dados atualizados
     * @return Feedback atualizado
     */
    public AdvisorFeedbackResponseDTO update(Long id, AdvisorFeedbackRequestDTO requestDTO) {
        repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Feedback não encontrado"));

        AdvisorFeeback updatedFeedback = AdvisorFeeback.builder()
                .createdAt(requestDTO.createdAt())
                .strengthsText(requestDTO.strengthsText())
                .weaknessesText(requestDTO.weaknessesText())
                .suggestionsText(requestDTO.suggestionsText())
                .build();

        return repository.save(updatedFeedback).convert();
    }

    /**
     * Edita os textos de um feedback específico.
     * @param id ID do feedback
     * @param strengths Novo texto de pontos fortes
     * @param weaknesses Novo texto de pontos fracos
     * @param suggestions Novas sugestões
     * @return Feedback atualizado
     */
    public AdvisorFeedbackResponseDTO editTexts(Long id, String strengths, String weaknesses, String suggestions) {
        AdvisorFeeback feedback = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Feedback não encontrado"));

        if (strengths != null) feedback.setStrengthsText(strengths);
        if (weaknesses != null) feedback.setWeaknessesText(weaknesses);
        if (suggestions != null) feedback.setSuggestionsText(suggestions);

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
     */
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NaoEncontradoException("Feedback não encontrado");
        }
        repository.deleteById(id);
    }
}
