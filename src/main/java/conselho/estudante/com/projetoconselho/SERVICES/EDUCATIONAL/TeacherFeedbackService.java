package conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL.TeacherFeedbackRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.TeacherFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.Council;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.TeacherFeeback;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL.CouncilRepository;
import conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL.TeacherFeedbackRepository;
import conselho.estudante.com.projetoconselho.REPOSITORIES.USERS.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pela gestão dos feedbacks de professores ({@link TeacherFeeback}).
 * Contém operações CRUD e manipulação de feedbacks por conselho e professor.
 */
@Service
@AllArgsConstructor
public class TeacherFeedbackService {

    private final TeacherFeedbackRepository repository;
    private final CouncilRepository councilRepository;
    private final TeacherRepository teacherRepository;

    /**
     * Cria um novo feedback de professor.
     * @param requestDTO Dados do feedback
     * @return Feedback criado
     */
    public TeacherFeedbackResponseDTO create(TeacherFeedbackRequestDTO requestDTO) {
        Council council = councilRepository.findById(requestDTO.councilId())
                .orElseThrow(() -> new NaoEncontradoException("Conselho não encontrado"));

        Teacher teacher = teacherRepository.findById(requestDTO.teacherId())
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));

        TeacherFeeback feedback = requestDTO.convert(council, teacher);
        return repository.save(feedback).convert();
    }

    /**
     * Atualiza um feedback existente.
     * @param id ID do feedback
     * @param requestDTO Dados atualizados
     * @return Feedback atualizado
     */
    public TeacherFeedbackResponseDTO update(Long id, TeacherFeedbackRequestDTO requestDTO) {
        TeacherFeeback feedback = repository.findById(id)
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
    public TeacherFeedbackResponseDTO editTexts(Long id, String strengths, String weaknesses, String suggestions) {
        TeacherFeeback feedback = repository.findById(id)
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
    public Page<TeacherFeedbackResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(TeacherFeeback::convert);
    }

    /**
     * Lista todos os feedbacks de um conselho específico com suporte a paginação.
     * @param councilId ID do conselho
     * @param pageable Configuração de paginação
     * @return Página de feedbacks do conselho
     */
    public Page<TeacherFeedbackResponseDTO> findByCouncil(Long councilId, Pageable pageable) {
        return repository.findByCouncilId(councilId, pageable).map(TeacherFeeback::convert);
    }

    /**
     * Busca um feedback pelo ID.
     * @param id ID do feedback
     * @return Feedback encontrado
     */
    public TeacherFeedbackResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(TeacherFeeback::convert)
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
