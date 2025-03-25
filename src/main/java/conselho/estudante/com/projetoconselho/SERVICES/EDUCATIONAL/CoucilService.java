package conselho.estudante.com.projetoconselho.SERVICES.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.EDUCATIONAL.CouncilRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ADMINISTRATION.SubjectResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.EDUCATIONAL.CouncilResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.*;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.ChangeItem;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.EditableItem;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.EDUCATIONAL.CouncilRepository;
import conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION.SUBJECT.SubjectService;
import conselho.estudante.com.projetoconselho.SERVICES.LOGS.CouncilLogsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Service responsável pela lógica de negócios relacionada a entidade {@link Council}.
 * Esta classe fornece métodos para criação, atualização, consulta e manipulação dos dados relacionados aos Conselhos Educacionais.
 *
 * @author joana voigt
 * @since 24/03/2025
 *
 * @see Council
 * @see CouncilRequestDTO
 * @see CouncilResponseDTO
 *
 * Atualizado em 25/03/2025
 * Conexão com o CouncilLogsService para gerar logs
 * @author Gustavo Stinghen
 * @see CouncilLogsService
 */
@Service
@AllArgsConstructor
public class CoucilService {

    private CouncilRepository repository;
    private CallToChatStudentService callToChatStudentService;
    private ViewedStudentService viewedStudentService;
    private CouncilLogsService logsService;

    /**
     * Verifica se o conselho está finalizado.
     *
     * @param council entitade do council para verificar.
     * @return booleano indicando se o conselho foi finalizado.
     */
    public boolean isCouncilFinished(Council council) {
        return council.getCouncilFinished();
    }

    /**
     * Cria um novo conselho baseado nas informações do DTO e armazena no repositório.
     *
     * @param councilRequestDTO DTO contendo os dados para criar um novo conselho.
     * @param actor Usuário que criou o conselho
     * @return DTO de resposta contendo o conselho criado.
     * @throws DadosDuplicadosException se já existir um conselho associado à mesma classe
     *                                  e não estiver finalizado.
     */
    public CouncilResponseDTO create(CouncilRequestDTO councilRequestDTO, User actor) {
        Council council = councilRequestDTO.convert();
        if(repository.existsByClasse(council.getClasse()) && !isCouncilFinished(council)) {
            throw new DadosDuplicadosException("Conselho já cadastrado");
        } else {
            callToChatStudentService.createCallToChatStudents( council );
            logsService.create(actor, council, "create");
            return repository.save(council).toDTO();
        }
    }


    /**
     * Atualiza um conselho existente com os novos dados.
     *
     * @param id Identificador do conselho a ser atualizado.
     * @param councilRequestDTO DTO contendo os novos dados do conselho.
     * @return DTO de resposta contendo o conselho atualizado.
     * @throws DadosDuplicadosException se outro conselho associado à mesma classe existir.
     */
    public CouncilResponseDTO update(Long id, CouncilRequestDTO councilRequestDTO) {
        Council council = councilRequestDTO.convert();
        if(repository.existsById(id)) {
            council.setId(id);
            if(repository.existsByClasse(council.getClasse())) {
                throw new DadosDuplicadosException("Conselho já cadastrado");
            } else {
                return repository.save(council).toDTO();
            }
        }
        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    private List<EditableItem> getEditableItems(Council oldCouncil, Council newCouncil, User actor) {

        List<EditableItem> changes = new ArrayList<>();

        if  ( oldCouncil.getDate() != newCouncil.getDate() ) {
            changes.add(new ChangeItem("date", oldCouncil.getDate(), newCouncil.getDate()));
            // Chamar a service da agenda para atualizar a data do conselho
        }

        if ( oldCouncil.getAdvisor() != newCouncil.getAdvisor() ) {
            changes.add(new ChangeItem("advisor", oldCouncil.getAdvisor(), newCouncil.getAdvisor()));
        }

        if ( oldCouncil.getCouncilFinished() != newCouncil.getCouncilFinished() ) {
            changes.add(new ChangeItem("councilFinished", oldCouncil.getCouncilFinished(), newCouncil.getCouncilFinished()));
        }

        if ( oldCouncil.getRepresentativePreCouncilFinished() != newCouncil.getRepresentativePreCouncilFinished() ) {
            changes.add(new ChangeItem("representativePreCouncilFinished", oldCouncil.getRepresentativePreCouncilFinished(), newCouncil.getRepresentativePreCouncilFinished()));
        }

        if ( oldCouncil.getFeedbackDelivered() != newCouncil.getFeedbackDelivered() ) {
            changes.add(new ChangeItem("feedbackDelivered", oldCouncil.getFeedbackDelivered(), newCouncil.getFeedbackDelivered()));

            if ( newCouncil.getFeedbackDelivered() ) {
                viewedStudentService.createViewedStudents(newCouncil);
            }
        }

        if ( oldCouncil.getTeacherPreCouncilFinished() != newCouncil.getTeacherPreCouncilFinished() ) {
            changes.add(new ChangeItem("teacherPreCouncilFinished", oldCouncil.getTeacherPreCouncilFinished(), newCouncil.getTeacherPreCouncilFinished()));
        }

        if ( oldCouncil.getRepresentativePreCouncilStarted() != newCouncil.getRepresentativePreCouncilStarted() ) {
            changes.add(new ChangeItem("representativePreCouncilStarted", oldCouncil.getRepresentativePreCouncilStarted(), newCouncil.getRepresentativePreCouncilStarted()));
        }

        if ( oldCouncil.getTeacherPreCouncilStarted() != newCouncil.getTeacherPreCouncilStarted() ) {
            changes.add(new ChangeItem("teacherPreCouncilStarted", oldCouncil.getTeacherPreCouncilStarted(), newCouncil.getTeacherPreCouncilStarted()));
        }


        return changes;
    }

    /**
     * Edita a data do conselho identificado pelo ID.
     *
     * @param id ID do conselho a ser atualizado.
     * @param date Nova data para o conselho.
     * @param actor Usuário que editou o conselho
     * @return DTO de resposta contendo o conselho atualizado com a nova data.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public CouncilResponseDTO editDate(Long id, Date date, User actor) {
        if(repository.existsById(id)) {
            Council council = repository.findById(id).get();
            Date oldDate = council.getDate();
            council.setDate(date);
            logsService.create(actor, council, Collections.singletonList(new ChangeItem("active", (Object) oldDate, (Object) date)), "update");
            // Chamar service da agenda para atualizar a data do conselho
            return repository.save(council).toDTO();
        }
        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    /**
     * Inicia o pré-conselho com os professores para o conselho identificado pelo ID.
     *
     * @param id Identificador do conselho.
     * @return DTO de resposta contendo o conselho atualizado.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public CouncilResponseDTO startTeacherPreCouncil(Long id) {
        if(repository.existsById(id)) {
            Council council = repository.findById(id).get();
            council.setTeacherPreCouncilStarted(true);
            return repository.save(council).toDTO();
        }
        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    public void generateTeacherPreCouncil() {

    }

    /**
     * Finaliza o pré-conselho com os professores para o conselho identificado pelo ID.
     *
     * @param id Identificador do conselho.
     * @return DTO de resposta contendo o conselho atualizado.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public CouncilResponseDTO endTeacherPreCouncil(Long id) {
        if(repository.existsById(id)) {
            Council council = repository.findById(id).get();
            council.setTeacherPreCouncilFinished(true);
            return repository.save(council).toDTO();
        }
        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    /**
     * Inicia o pré-conselho com os representantes para o conselho identificado pelo ID.
     *
     * @param id Identificador do conselho.
     * @return DTO de resposta contendo o conselho atualizado.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public CouncilResponseDTO startRepresentativePreCouncil(Long id) {
        if(repository.existsById(id)) {
            Council council = repository.findById(id).get();
            council.setRepresentativePreCouncilStarted(true);
            return repository.save(council).toDTO();
        }
        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    /**
     * Finaliza o pré-conselho com os representantes para o conselho identificado pelo ID.
     *
     * @param id Identificador do conselho.
     * @return DTO de resposta contendo o conselho atualizado.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public CouncilResponseDTO endRepresentativePreCouncil(Long id) {
        if(repository.existsById(id)) {
            Council council = repository.findById(id).get();
            council.setRepresentativePreCouncilFinished(true);
            return repository.save(council).toDTO();
        }
        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    /**
     * Finaliza o conselho identificado pelo ID.
     *
     * @param id Identificador do conselho.
     * @return DTO de resposta contendo o conselho atualizado.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public CouncilResponseDTO endCouncil(Long id) {
        if(repository.existsById(id)) {
            Council council = repository.findById(id).get();
            council.setCouncilFinished(true);
            return repository.save(council).toDTO();
        }
        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    /**
     * Entrega o feedback para o conselho identificado pelo ID.
     *
     * @param id Identificador do conselho.
     * @return DTO de resposta contendo o conselho com feedback entregue.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public CouncilResponseDTO deliverFeedback(Long id) {
        if(repository.existsById(id)) {
            Council council = repository.findById(id).get();
            council.setFeedbackDelivered(true);
            return repository.save(council).toDTO();
        }
        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    /**
     * Encontra os professores associados a um conselho específico.
     *
     * @param Councilid Identificador do conselho.
     * @param pageable Informações de paginação.
     * @return Lista de professores associados ao conselho.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public Page<Teacher> findTeachersByCouncil(Long Councilid, Pageable pageable) {
        Council council = repository.findById(Councilid)
                .orElseThrow(() -> new NaoEncontradoException("Conselho nao encontrado"));
        List<Teacher> teachers = council.getTeachers();
        return new PageImpl<>(teachers, pageable, teachers.size());
    }

    /**
     * Encontra um conselho pelo seu identificador.
     *
     * @param id Identificador do conselho.
     * @return DTO de resposta contendo o conselho.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public CouncilResponseDTO findById(Long id) {
        if(repository.existsById(id)) {
            return repository.findById(id).get().toDTO();
        }
        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    /**
     * Adiciona a lista de professores do pré-conselho ao conselho.
     *
     * @param id Identificador do conselho.
     * @param teacherPreCouncils Lista de professores do pré-conselho.
     * @return DTO de resposta contendo o conselho atualizado.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public CouncilResponseDTO addTeacherPreCouncil(Long id, List<TeacherPreCouncil> teacherPreCouncils) {
        if(repository.existsById(id)) {
            Council council = repository.findById(id).get();
            council.setTeacherPreCouncils(teacherPreCouncils);
            return repository.save(council).toDTO();
        }
        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    /**
     * Adiciona o representante ao pré-conselho do conselho identificado pelo ID.
     *
     * @param id Identificador do conselho.
     * @param representativePreCouncil Representante do pré-conselho.
     * @return DTO de resposta contendo o conselho atualizado.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public CouncilResponseDTO addRepresentativePreCouncil(Long id, RepresentativePreCouncil representativePreCouncil) {
        if(repository.existsById(id)) {
            Council council = repository.findById(id).get();
            council.setRepresentativePreCouncil(representativePreCouncil);
            return repository.save(council).toDTO();
        }
        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    /**
     * Adiciona feedbacks pessoais ao conselho identificado pelo ID.
     *
     * @param id Identificador do conselho.
     * @param feedbacks Lista de feedbacks pessoais.
     * @return DTO de resposta contendo o conselho atualizado.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public CouncilResponseDTO addFeedback(Long id, List<PersonalFeedback> feedbacks) {
        if(repository.existsById(id)) {
            Council council = repository.findById(id).get();
            council.setFeedbacks(feedbacks);
            return repository.save(council).toDTO();
        }
        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    /**
     * Adiciona feedbacks de classe ao conselho identificado pelo ID.
     *
     * @param id Identificador do conselho.
     * @param classFeedback Feedback da classe.
     * @return DTO de resposta contendo o conselho atualizado.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public CouncilResponseDTO addClassFeedback(Long id, ClassFeedback classFeedback) {
        if(repository.existsById(id)) {
            Council council = repository.findById(id).get();
            council.setClassFeedback(classFeedback);
            return repository.save(council).toDTO();
        }
        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    /**
     * Encontra os alunos que visualizaram o conselho.
     *
     * @param councilId Identificador do conselho.
     * @param pageable Informações de paginação.
     * @return Lista de alunos que visualizaram o conselho.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public Page<Student> findViewedStudents(Long councilId, Pageable pageable) {
        Council council = repository.findById(councilId)
                .orElseThrow(() -> new NaoEncontradoException("Conselho nao encontrado"));

        return viewedStudentService.listAllStudents(pageable, council);
    }

    /**
     * Encontra os alunos que precisam ser chamados para o conselho.
     *
     * @param councilId Identificador do conselho.
     * @param pageable Informações de paginação.
     * @return Lista de alunos que precisam ser chamados.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public Page<Student> findStudentsWhoNeedToBeCalled(Long councilId, Pageable pageable) {
        Council council = repository.findById(councilId)
                .orElseThrow(() -> new NaoEncontradoException("Conselho não encontrado"));

        return callToChatStudentService.listAllStudents(pageable, council);
    }

    /**
     * Deleta um conselho identificado pelo ID.
     *
     * @param id Identificador do conselho a ser deletado.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NaoEncontradoException("Conselho nao encontrado");
        }
    }
}

