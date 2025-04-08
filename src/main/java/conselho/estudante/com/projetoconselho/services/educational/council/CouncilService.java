package conselho.estudante.com.projetoconselho.services.educational.council;

import conselho.estudante.com.projetoconselho.models.dto.request.educational.CouncilRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.request.educational.TeacherPreCouncilRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.educational.CouncilResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Subject;
import conselho.estudante.com.projetoconselho.models.entity.educational.*;
import conselho.estudante.com.projetoconselho.models.entity.logs.AddItem;
import conselho.estudante.com.projetoconselho.models.entity.logs.ChangeItem;
import conselho.estudante.com.projetoconselho.models.entity.logs.EditableItem;
import conselho.estudante.com.projetoconselho.models.entity.users.Student;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.models.exceptions.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.educational.CouncilRepository;
import conselho.estudante.com.projetoconselho.services.administration.NotificationService;
import conselho.estudante.com.projetoconselho.services.educational.CallToChatStudentService;
import conselho.estudante.com.projetoconselho.services.educational.FeedbackGroupService;
import conselho.estudante.com.projetoconselho.services.educational.teacher_pre_council.TeacherPreCouncilService;
import conselho.estudante.com.projetoconselho.services.educational.ViewedStudentService;
import conselho.estudante.com.projetoconselho.services.logs.CouncilLogsService;
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
 * Atualizado em 27/03/2025
 * Conexão com o CouncilLogsService para gerar logs
 * Conexão com as services associadas
 * Falta conectar RepresentativePreCouncilService e NotificationService
 * @author Gustavo Stinghen
 * @see CouncilLogsService
 *
 * Atualizado em 01/04/2025
 * Conexão com o NotificationService para envio de notificação
 * @author Gustavo Stinghen
 * @see NotificationService
 */
@Service
@AllArgsConstructor
public class CouncilService {

    private CouncilRepository repository;
    private CallToChatStudentService callToChatStudentService;
    private ViewedStudentService viewedStudentService;
    private CouncilLogsService logsService;
    private TeacherPreCouncilService teacherPreCouncilService;
    private NotificationService notificationService;
    private FeedbackGroupService feedbackGroupService;

    /**
     * Cria um novo conselho baseado nas informações do DTO e armazena no repositório.
     *
     * @param councilRequestDTO DTO contendo os dados para criar um novo conselho.
     * @param actor O usuário que criou o conselho.
     * @return DTO de resposta contendo o conselho criado.
     * @throws DadosDuplicadosException se já existir um conselho associado à mesma classe
     *                                  e não estiver finalizado.
     */
    public CouncilResponseDTO create(CouncilRequestDTO councilRequestDTO, User actor) {

        try {
            Council council = councilRequestDTO.convert();
            if(repository.existsByClasse(council.getClasse()) && ! council.getCouncilFinished()) {
                throw new DadosDuplicadosException("Conselho já cadastrado");
            } else {

                council.setCreatedAt(new Date());
                council.setCouncilFinished(false);
                council.setFeedbackDelivered(false);
                council.setRepresentativePreCouncilFinished(false);
                council.setTeacherPreCouncilFinished(false);
                council.setRepresentativePreCouncilStarted(false);
                council.setTeacherPreCouncilStarted(false);

                logsService.create(actor, council, "create");
                return repository.save(council).toDTO();
            }
        } catch (Exception e) {
            throw new DadosDuplicadosException("Conselho já cadastrado");
        }
    }


    /**
     * Atualiza um conselho existente com os novos dados.
     *
     * @param id Identificador do conselho a ser atualizado.
     * @param councilRequestDTO DTO contendo os novos dados do conselho.
     * @param actor O usuário que atualizou o conselho.
     * @return DTO de resposta contendo o conselho atualizado.
     * @throws DadosDuplicadosException se outro conselho associado à mesma classe existir.
     */
    public CouncilResponseDTO update(Long id, CouncilRequestDTO councilRequestDTO, User actor) {
        Council council = councilRequestDTO.convert();
        if(repository.existsById(id)) {
            council.setId(id);
            if(repository.existsByClasse(council.getClasse())) {
                throw new DadosDuplicadosException("Conselho já cadastrado");
            } else {
                logsService.create(actor, council, getEditableItems(repository.findById(id).get(), council), "update");
                return repository.save(council).toDTO();
            }
        }
        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    /**
     * Método auxiliar para gerar logs que mostra os campos que foram editados
     * @param oldCouncil o conselho antigo
     * @param newCouncil o conselho novo
     * @return uma lista com os campos editados
     */
    private List<EditableItem> getEditableItems(Council oldCouncil, Council newCouncil) {

        List<EditableItem> changes = new ArrayList<>();

        if ( ! oldCouncil.getFeedbackDelivered().equals( newCouncil.getFeedbackDelivered() ) ) {
            changes.add(new ChangeItem("feedbackDelivered", (Object) oldCouncil.getFeedbackDelivered(), (Object) newCouncil.getFeedbackDelivered()));

            if ( newCouncil.getFeedbackDelivered() ) {
                newCouncil.setCouncilFinished(true);
                generateStudentNotification( newCouncil );
                generateTeacherNotification( newCouncil, "Você recebeu um novo feedback da turma " + newCouncil.getClasse().getAcronym() );
            }
        }

        if ( ! oldCouncil.getCouncilFinished().equals( newCouncil.getCouncilFinished() ) ) {
            changes.add(new ChangeItem("councilFinished", (Object) oldCouncil.getCouncilFinished(), (Object) newCouncil.getCouncilFinished()));

            if ( newCouncil.getCouncilFinished() ) {

                newCouncil.setTeacherPreCouncilFinished(true);
                newCouncil.setRepresentativePreCouncilFinished(true);
                newCouncil.setTeacherPreCouncilStarted(true);
                newCouncil.setRepresentativePreCouncilStarted(true);
            }
        }

        if ( ! oldCouncil.getRepresentativePreCouncilStarted().equals( newCouncil.getRepresentativePreCouncilStarted() ) ) {
            changes.add(new ChangeItem("representativePreCouncilStarted", (Object) oldCouncil.getRepresentativePreCouncilStarted(), (Object) newCouncil.getRepresentativePreCouncilStarted()));

            if ( newCouncil.getRepresentativePreCouncilStarted() ) {
                generateRepresentativeNotification( newCouncil );
            }
        }

        if ( ! oldCouncil.getTeacherPreCouncilStarted().equals( newCouncil.getTeacherPreCouncilStarted() ) ) {
            changes.add(new ChangeItem("teacherPreCouncilStarted", (Object) oldCouncil.getTeacherPreCouncilStarted(), (Object) newCouncil.getTeacherPreCouncilStarted()));

            if ( newCouncil.getTeacherPreCouncilStarted() ) {
                generateTeacherNotification( newCouncil, "Você tem um pré conselho para ser escrito da turma " + newCouncil.getClasse().getAcronym() );
            }
        }

        if ( ! oldCouncil.getRepresentativePreCouncilFinished().equals( newCouncil.getRepresentativePreCouncilFinished() ) ) {
            changes.add(new ChangeItem("representativePreCouncilFinished", (Object) oldCouncil.getRepresentativePreCouncilFinished(), (Object) newCouncil.getRepresentativePreCouncilFinished()));
        }

        if ( ! oldCouncil.getTeacherPreCouncilFinished().equals( newCouncil.getTeacherPreCouncilFinished() ) ) {
            changes.add(new ChangeItem("teacherPreCouncilFinished", (Object) oldCouncil.getTeacherPreCouncilFinished(), (Object) newCouncil.getTeacherPreCouncilFinished()));
        }

        if ( ! oldCouncil.getDate().equals( newCouncil.getDate() ) ) {
            changes.add(new ChangeItem("date", (Object) oldCouncil.getDate(), (Object) newCouncil.getDate()));
            // TODO: Service para alterar data na agenda
        }

        if ( ! oldCouncil.getAdvisor().equals( newCouncil.getAdvisor() ) ) {
            changes.add(new ChangeItem("advisor", (Object) oldCouncil.getAdvisor(), (Object) newCouncil.getAdvisor()));
        }

        if ( ! oldCouncil.getTeacherPreCouncilEndDate() .equals( newCouncil.getTeacherPreCouncilEndDate() ) ) {
            changes.add(new ChangeItem("teacherPreCouncilEndDate", (Object) oldCouncil.getTeacherPreCouncilEndDate(), (Object) newCouncil.getTeacherPreCouncilEndDate()));

            if ( newCouncil.getTeacherPreCouncilEndDate().before( new Date() ) ) {
                endTeacherPreCouncil(newCouncil.getId());
            }
        }

        if ( ! oldCouncil.getRepresentativePreCouncilEndDate() .equals( newCouncil.getRepresentativePreCouncilEndDate() ) ) {
            changes.add(new ChangeItem("representativePreCouncilEndDate", (Object) oldCouncil.getRepresentativePreCouncilEndDate(), (Object) newCouncil.getRepresentativePreCouncilEndDate()));

            if ( newCouncil.getRepresentativePreCouncilEndDate().before( new Date() ) ) {
                endRepresentativePreCouncil(newCouncil.getId());
            }
        }

        return changes;
    }

    public void generateRepresentativeNotification( Council council ) {

        List<Student> students = council.getClasse().getRepresentative().getStudents();

        for ( Student student : students ) {
            notificationService.create(
                    student,
                    "Você tem um pré conselho para ser escrito da turma " + council.getClasse().getAcronym()
                    , true);
        }

    }

    public void generateTeacherNotification( Council council, String message ) {

        List<AvaliableTeacher> teachers = council.getTeachers();

        for ( AvaliableTeacher teacher : teachers ) {
            notificationService.create(
                    teacher.getTeacher(),
                    message
                    , false);
        }
    }

    public void generateStudentNotification( Council council) {

        List<Student> students = council.getClasse().getStudents();

        for ( Student student : students ) {
            notificationService.create(
                    student,
                    "Você recebeu um novo feedback na turma " + council.getClasse().getAcronym()
                    , false);
        }
    }

    /**
     * Edita a data do conselho identificado pelo ID.
     *
     * @param id ID do conselho a ser atualizado.
     * @param date Nova data para o conselho.
     * @param actor O usuário que editou o conselho.
     * @return DTO de resposta contendo o conselho atualizado com a nova data.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public CouncilResponseDTO editDate(Long id, Date date, User actor) {
        if(repository.existsById(id)) {
            Council council = repository.findById(id).get();
            Date oldDate = council.getDate();
            council.setDate(date);
            logsService.create(actor, council, Collections.singletonList(new ChangeItem("date", (Object) oldDate, (Object) date)), "update");
            // TODO: Service para alterar data na agenda
            return repository.save(council).toDTO();
        }
        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    /**
     * Edita a data de fim do pré-conselho do representante do conselho identificado pelo ID.
     *
     * @param id ID do conselho a ser atualizado.
     * @param endDate Nova data de fim do pré-conselho do representante.
     * @param actor O usuário que editou o conselho.
     * @return DTO de resposta contendo o conselho atualizado com a nova data de fim do pré-conselho do representante.
     * @throws NaoEncontradoException se o conselho nao for encontrado.
     * @author Gustavo Stinghen
     * @since 27/03/2025
     */
    public CouncilResponseDTO editTeacherPreCouncilEndDate(Long id, Date endDate, User actor) {
        if(repository.existsById(id)) {
            Council council = repository.findById(id).get();
            logsService.create(actor, council, Collections.singletonList(new ChangeItem("representativePreCouncilEndDate", (Object) council.getRepresentativePreCouncilEndDate(), (Object) endDate)), "update");
            council.setRepresentativePreCouncilEndDate(endDate);

            if ( endDate.before(new Date()) ) {
                this.endTeacherPreCouncil(council.getId());
            }

           return repository.save(council).toDTO();
        }
        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    /**
     * Edita a data de fim do pré-conselho do representante do conselho identificado pelo ID.
     *
     * @param id ID do conselho a ser atualizado.
     * @param endDate Nova data de fim do pré-conselho do representante.
     * @param actor O usuário que editou o conselho.
     * @return DTO de resposta contendo o conselho atualizado com a nova data de fim do pré-conselho do representante.
     * @throws NaoEncontradoException se o conselho nao for encontrado.
     * @author Gustavo Stinghen
     * @since 27/03/2025
     */
    public CouncilResponseDTO editRepresentativePreCouncilEndDate(Long id, Date endDate, User actor) {
        if(repository.existsById(id)) {
            Council council = repository.findById(id).get();
            logsService.create(actor, council, Collections.singletonList(new ChangeItem("representativePreCouncilEndDate", (Object) council.getRepresentativePreCouncilEndDate(), (Object) endDate)), "update");
            council.setRepresentativePreCouncilEndDate(endDate);

            if ( endDate.before(new Date()) ) {
                this.endRepresentativePreCouncil(council.getId());
            }

            return repository.save(council).toDTO();
        }

        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    /**
     * Inicia o pré-conselho com os professores para o conselho identificado pelo ID.
     *
     * @param id Identificador do conselho.
     * @param endDate Data de fim do pré-conselho.
     * @return DTO de resposta contendo o conselho atualizado.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public CouncilResponseDTO startTeacherPreCouncil(Long id, Date endDate) {
        if(repository.existsById(id)) {
            Council council = repository.findById(id).get();
            council.setTeacherPreCouncilStarted(true);
            council.setTeacherPreCouncilEndDate(endDate);
            generateTeacherPreCouncil(council);
            return repository.save(council).toDTO();
        }
        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    private void generateTeacherPreCouncil(Council council) {

        List<AvaliableTeacher> avaliableTeachers = council.getTeachers();

        if ( avaliableTeachers.isEmpty() ) {
            return;
        }

        generateTeacherNotification(council, "Você tem um pré conselho para ser escrito da turma " + council.getClasse().getAcronym() );

        for (AvaliableTeacher availableTeacher : avaliableTeachers) {

            for ( Subject subject : availableTeacher.getSubjects() ) {

                teacherPreCouncilService.create(
                        TeacherPreCouncilRequestDTO.builder()
                                .teacher(availableTeacher.getTeacher())
                                .council(council)
                                .subject(subject)
                                .endDate(council.getTeacherPreCouncilEndDate())
                                .build()
                );

            }
        }

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
     * Obtem os conselhos ativos com o pré-conselho com os professores.
     *
     * @param date Data de fim do pré-conselho.
     * @return Lista de conselhos ativos com o pré-conselho com os professores.
     * @author Gustavo Stinghen
     * @since 27/03/2025
     */
    public List<Council> getActiveCouncils( Date date ) {
        return repository.findOpenCouncils( date );
    }

    /**
     * Inicia o pré-conselho com os representantes para o conselho identificado pelo ID.
     *
     * @param id Identificador do conselho.
     * @param endDate Data de fim do pré-conselho.
     * @return DTO de resposta contendo o conselho atualizado.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public CouncilResponseDTO startRepresentativePreCouncil(Long id, Date endDate) {
        if(repository.existsById(id)) {
            Council council = repository.findById(id).get();
            council.setRepresentativePreCouncilStarted(true);
            council.setRepresentativePreCouncilEndDate(endDate);
            generateRepresentativeNotification(council);
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
            council.setFeedbackDeliveredDate(new Date());
            generateFeedbackGroups(council);
            generateStudentNotification(council);
            generateTeacherNotification(council,"Você recebeu um novo feedback na turma " + council.getClasse().getAcronym());
            return repository.save(council).toDTO();
        }
        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    /**
     * Método auxiliar que cria os grupos de feedback para um conselho.
     * @param council Conselho para o qual os grupos de feedback serão criados.
     * @author Gustavo Stinghen
     * @since 27/03/2025
     */
    private void generateFeedbackGroups (Council council) {

        List<PersonalFeedback> feedbacks = council.getFeedbacks();

        for (PersonalFeedback feedback : feedbacks) {

            feedbackGroupService.create(
                    feedback,
                    council.getClassFeedback(),
                    council.getFeedbackDeliveredDate()
            );
        }
    }

    /**
     * Encontra os professores associados a um conselho específico.
     *
     * @param Councilid Identificador do conselho.
     * @param pageable Informações de paginação.
     * @return Lista de professores associados ao conselho.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public Page<AvaliableTeacher> findTeachersByCouncil(Long Councilid, Pageable pageable) {
        Council council = repository.findById(Councilid)
                .orElseThrow(() -> new NaoEncontradoException("Conselho nao encontrado"));
        List<AvaliableTeacher> teachers = council.getTeachers();
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
     * Adiciona o professor ao pré-conselho do conselho identificado pelo ID.
     *
     * @param id Identificador do conselho.
     * @param teacherPreCouncil Professor do pré-conselho.
     * @return DTO de resposta contendo o conselho atualizado.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public CouncilResponseDTO addTeacherPreCouncil(Long id, TeacherPreCouncil teacherPreCouncil, User actor) {
        if(repository.existsById(id)) {
            Council council = repository.findById(id).get();
            council.getTeacherPreCouncils().add(teacherPreCouncil);
            logsService.create( actor, council, Collections.singletonList( new AddItem("teacherPreCouncils", (Object) teacherPreCouncil ) ), "add" );
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
    public CouncilResponseDTO updateRepresentativePreCouncil(Long id, RepresentativePreCouncil representativePreCouncil) {
        if(repository.existsById(id)) {
            Council council = repository.findById(id).get();
            council.setRepresentativePreCouncil(representativePreCouncil);
            logsService.create( council, Collections.singletonList( new ChangeItem("representativePreCouncil", (Object) null, (Object) representativePreCouncil ) ), "update" );
            return repository.save(council).toDTO();
        }
        throw new NaoEncontradoException("Conselho nao encontrado");
    }

    /**
     * Adiciona feedback pessoal ao conselho identificado pelo ID.
     *
     * @param id Identificador do conselho.
     * @param feedback Feedback pessoal.
     * @param actor O usuário que criou o feedback pessoal.
     * @return DTO de resposta contendo o conselho atualizado.
     * @throws NaoEncontradoException se o conselho nao for encontrado.
     */
    public CouncilResponseDTO addFeedback(Long id, PersonalFeedback feedback, User actor) {
        if(repository.existsById(id)) {
            Council council = repository.findById(id).get();
            council.getFeedbacks().add(feedback);
            logsService.create( actor, council, Collections.singletonList( new AddItem("feedbacks", (Object) feedback ) ), "add" );
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
    public CouncilResponseDTO updateClassFeedback(Long id, ClassFeedback classFeedback) {
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
     * @param actor O usuário que deletou o conselho.
     * @throws NaoEncontradoException se o conselho não for encontrado.
     */
    public void delete(Long id, User actor) {
        try {

            if (!repository.existsById(id)) {
                throw new NaoEncontradoException("Conselho nao encontrado");
            }

            logsService.create( actor, repository.findById(id).get(), "delete" );
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NaoEncontradoException("Conselho nao encontrado");
        }
    }

    /**
     * Encontra todos os conselhos.
     *
     * @param pageable Informações de paginação.
     * @return Página contendo todos os conselhos.
     * @author Gustavo Stinghen
     * @since 07/04/2025
     */
    public Page<CouncilResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(Council::toDTO);
    }
}

