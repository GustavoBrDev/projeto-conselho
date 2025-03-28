package conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.FeedbackGroupResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.ClassFeedback;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.FeedbackGroup;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.PersonalFeedback;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL.FeedbackGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Serviço responsável pela gestão dos grupos de feedbacks ({@link FeedbackGroup}).
 * Contém operações CRUD e métodos para manipulação de feedbacks por conselho.
 * @author Camilly
 * @since 19/03/2025
 *
 * Atualização em 27/03/2025
 * Reestruturação da service, pois ela é interna
 * @author Gustavo Stinghen
 */
@Service
@AllArgsConstructor
public class FeedbackGroupService {

    private final FeedbackGroupRepository repository;

    /**
     * Cria um novo grupo de feedbacks.
     *
     * @param personalFeedback PersonalFeedback associado ao grupo
     * @param classFeedback    ClassFeedback associado ao grupo
     * @param date             Data do grupo
     */
    public void create(PersonalFeedback personalFeedback, ClassFeedback classFeedback, Date date) {

        FeedbackGroup group = FeedbackGroup.builder()
                .date(date)
                .personalFeedback(personalFeedback)
                .classFeedback(classFeedback)
                .build();

        repository.save(group);
    }

    /**
     * Edita um grupo de feedbacks.
     *
     * @param id               ID do grupo
     * @param personalFeedback PersonalFeedback associado ao grupo
     * @param classFeedback    ClassFeedback associado ao grupo
     * @param date             Data do grupo
     */
    public void update( Long id, PersonalFeedback personalFeedback, ClassFeedback classFeedback, Date date) {

        if (!repository.existsById(id)) {
            throw new NaoEncontradoException("Grupo de feedbacks nao encontrado");
        }

        FeedbackGroup group = FeedbackGroup.builder()
                .id(id)
                .date(date)
                .personalFeedback(personalFeedback)
                .classFeedback(classFeedback)
                .build();

        repository.save(group);
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
     * Lista grupos de feedbacks por aluno com paginação.
     *
     * @param studentId ID do aluno
     * @param pageable  Configuração da paginação
     * @return Página contendo os grupos de feedbacks do aluno
     */
    public Page<FeedbackGroupResponseDTO> findByStudent(Long studentId, Pageable pageable) {

        try {
            return repository.findByPersonalFeedback_Student_Id(studentId, pageable).map(FeedbackGroup::convert);
        } catch (NaoEncontradoException e) {
            throw new NaoEncontradoException("Aluno nao encontrado");
        }
    }

    /**
     * Lista grupos de feedbacks por turma com paginação.
     *
     * @param classId  ID da turma
     * @param pageable Configuração da paginação
     * @return Página contendo os grupos de feedbacks da turma
     */
    public Page<FeedbackGroupResponseDTO> findByClass(Long classId, Pageable pageable) {
        return repository.findByClassFeedback_Classe_Id(classId, pageable)
                .map(FeedbackGroup::convert);
    }

    /**
     * Lista todos os grupos de feedbacks de um conselho específico com paginação.
     *
     * @param councilId ID do conselho
     * @param pageable  Configuração da paginação
     * @return Página contendo os grupos de feedbacks do conselho
     */
    public Page<FeedbackGroupResponseDTO> findByCouncil(Long councilId, Pageable pageable) {
        return repository.findByClassFeedback_Council_Id(councilId, pageable).map(FeedbackGroup::convert);
    }

    /**
     * Busca um grupo de feedbacks pelo ID.
     *
     * @param id ID do grupo
     * @return Grupo de feedbacks encontrado
     */
    public FeedbackGroupResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(FeedbackGroup::convert)
                .orElseThrow(() -> new NaoEncontradoException("Grupo de feedbacks não encontrado"));
    }


    /**
     * Filtra os grupos de feedbacks por turma.
     *
     * @param classId  ID da turma
     * @param pageable Configuração da paginação
     * @return Página contendo os grupos de feedbacks filtrados por turma
     */
    public Page<FeedbackGroupResponseDTO> filterByClass(Long classId, Pageable pageable) {
        return repository.findByClassFeedback_Classe_Id(classId, pageable).map(FeedbackGroup::convert);
    }

    /**
     * Realiza uma pesquisa inteligente nos grupos de feedbacks.
     * Pode buscar por atributos do feedback pessoal, como turma, curso e data.
     *
     * @param term     Termo de busca
     * @param pageable Configuração da paginação
     * @return Página contendo os grupos de feedbacks que correspondem ao termo pesquisado
     */
    public Page<FeedbackGroupResponseDTO> smartSearch(String term, Pageable pageable) {
        // TODO: Implementar pesquisa inteligente (Camilly)
        //return repository.searchByPersonalFeedbackAttributes(term, pageable).map(FeedbackGroup::convert);
        return null;
    }

    /**
     * Deleta um grupo de feedbacks pelo ID.
     *
     * @param id ID do grupo a ser deletado
     * @throws NaoEncontradoException Se o grupo de feedbacks nao for encontrado
     *
     * Atualizado em 27/03/2025
     * Adicionado breve tratamento de erro
     * @author Gustavo Stinghen
     */
    public void delete(Long id) {

        try {

            if (!repository.existsById(id)) {
                throw new NaoEncontradoException("Grupo de feedbacks nao encontrado");
            }

            repository.deleteById(id);
        } catch (Exception e) {
            throw new NaoEncontradoException("Grupo de feedbacks nao encontrado");
        }
    }
}
