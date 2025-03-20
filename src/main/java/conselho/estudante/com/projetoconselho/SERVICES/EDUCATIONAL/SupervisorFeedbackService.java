package conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL.SupervisorFeedbackRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.SupervisorFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.SupervisorFeedback;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Supervisor;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL.CouncilRepository;
import conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL.SupervisorFeedbackRepository;
import conselho.estudante.com.projetoconselho.REPOSITORIES.USERS.SupervisorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Serviço responsável pela gestão dos feedbacks de supervisores ({@link SupervisorFeedback}).
 * Contém operações CRUD e manipulação de feedbacks por conselho e supervisor.
 * @author Camilly Chelest
 * @since 19/03/2025
 */
@Service
@AllArgsConstructor
public class SupervisorFeedbackService {

    private final SupervisorFeedbackRepository repository;
    private final CouncilRepository councilRepository;
    private final SupervisorRepository supervisorRepository;

    /**
     * Cria um novo feedback de supervisor.
     * @param requestDTO Dados do feedback
     * @return Feedback criado
     */
    public SupervisorFeedbackResponseDTO create(SupervisorFeedbackRequestDTO requestDTO) {
        Council council = councilRepository.findById(requestDTO.councilId())
                .orElseThrow(() -> new NaoEncontradoException("Conselho não encontrado"));

        Supervisor supervisor = supervisorRepository.findById(requestDTO.supervisorId())
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));

        SupervisorFeedback feedback = requestDTO.convert(council, supervisor);
        return repository.save(feedback).convert();
    }

    /**
     * Atualiza um feedback existente.
     * @param id ID do feedback
     * @param requestDTO Dados atualizados
     * @return Feedback atualizado
     */
    public SupervisorFeedbackResponseDTO update(Long id, SupervisorFeedbackRequestDTO requestDTO) {
        SupervisorFeedback feedback = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Feedback não encontrado"));

        feedback.setCreatedAt(requestDTO.createdAt());
        feedback.setStrengthsText(requestDTO.strengthsText());
        feedback.setWeaknessesText(requestDTO.weaknessesText());
        feedback.setSuggestionsText(requestDTO.suggestionsText());

        return repository.save(feedback).convert();
    }

    /**
     * Edita os textos de um feedback específico.
     * @param id ID do feedback
     * @param strengths Novo texto de pontos fortes
     * @param weaknesses Novo texto de pontos fracos
     * @param suggestions Novas sugestões
     * @return Feedback atualizado
     */
    public SupervisorFeedbackResponseDTO editTexts(Long id, String strengths, String weaknesses, String suggestions) {
        SupervisorFeedback feedback = repository.findById(id)
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
