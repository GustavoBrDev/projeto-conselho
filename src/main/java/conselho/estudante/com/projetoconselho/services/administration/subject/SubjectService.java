package conselho.estudante.com.projetoconselho.services.administration.subject;

import conselho.estudante.com.projetoconselho.models.dto.request.administration.SubjectRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.administration.SubjectResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Subject;
import conselho.estudante.com.projetoconselho.models.entity.logs.AddItem;
import conselho.estudante.com.projetoconselho.models.entity.logs.ChangeItem;
import conselho.estudante.com.projetoconselho.models.entity.logs.EditableItem;
import conselho.estudante.com.projetoconselho.models.entity.users.Teacher;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.models.exceptions.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.administration.SubjectRepository;
import conselho.estudante.com.projetoconselho.services.logs.SubjectLogsService;
import conselho.estudante.com.projetoconselho.services.users.TeacherService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Serviço para gerenciar operações relacionadas à entidade {@link Subject}.
 *
 * @author Joana Voigt
 * @since 17/03/2025
 *
 * @see Subject
 * @see SubjectRequestDTO
 * @see SubjectResponseDTO
 *
 * Atualizado em 21/03/2025
 * Conexão com o UserLogsService para gerar logs
 * @author Gustavo Stinghen
 * @see SubjectLogsService
 */
@Service
@AllArgsConstructor
public class SubjectService {

    private final SubjectRepository repository;
    private final TeacherService teacherService;
    private final SubjectLogsService logsService;

    /**
     * Cria uma nova matéria.
     *
     * @param subjectRequestDTO Dados da matéria a ser criada.
     * @param actor Usuário que criou a matéria.
     * @return DTO da resposta contendo a matéria criada.
     * @throws DadosDuplicadosException se a matéria já existir.
     */
    public SubjectResponseDTO create(SubjectRequestDTO subjectRequestDTO, User actor) {
        Subject subject = subjectRequestDTO.convert();
        if (repository.existsByName(subject.getName())) {
            throw new DadosDuplicadosException("Matéria já cadastrada");
        } else {
            subject.setCreatedAt( new Date());
            subject = repository.save(subject);
            logsService.create(actor, subject, "create");
            return subject.toDTO();
        }
    }

    /**
     * Atualiza uma matéria existente.
     *
     * @param id ID da matéria a ser atualizada.
     * @param subjectRequestDTO Novos dados da matéria.
     * @param actor Usuário que atualizou a matéria.
     * @return DTO da resposta contendo a matéria atualizada.
     * @throws DadosDuplicadosException se a matéria já existir.
     * @throws NaoEncontradoException se a matéria não for encontrada.
     */
    public SubjectResponseDTO update(Long id, SubjectRequestDTO subjectRequestDTO, User actor) {
        Subject subject = subjectRequestDTO.convert();
        if (repository.existsById(id)) {
            subject.setId(id);
            if (repository.existsByName(subject.getName())) {
                throw new DadosDuplicadosException("Matéria já cadastrada");
            } else {
                subject.setCreatedAt( repository.findById(id).get().getCreatedAt() );
                logsService.create(actor, subject, getEditableItems(repository.findById(id).get(), subject), "update");
                return repository.save(subject).toDTO();
            }
        }
        throw new NaoEncontradoException("Matéria nao encontrada");
    }

    private List<EditableItem> getEditableItems(Subject oldSubject, Subject newSubject) {

        List<EditableItem> changes = new ArrayList<>();

        if (!oldSubject.getName().equals(newSubject.getName())) {
            changes.add(new ChangeItem("name", oldSubject.getName(), newSubject.getName()));
        }

        if (oldSubject.getWorkLoad() != newSubject.getWorkLoad()) {
            changes.add(new ChangeItem("workLoad", oldSubject.getWorkLoad(), newSubject.getWorkLoad()));
        }

        return changes;
    }

    /**
     *
     * Edita o nome de uma matéria específica.
     *
     * @param id ID da matéria.
     * @param name Novo nome.
     * @param actor Usuário que editou o nome.
     * @return DTO da resposta contendo a matéria atualizada.
     */
    public SubjectResponseDTO editName(Long id, String name, User actor) {
        Subject subject = repository.findById(id).get();
        logsService.create(actor, subject, Collections.singletonList(new ChangeItem("name", (Object) subject.getName(), (Object) name)), "update");
        subject.setName(name);
        return repository.save(subject).toDTO();
    }

    /**
     * Edita a carga horária de uma matéria específica.
     *
     * @param id ID da matéria.
     * @param workLoad Nova carga horária.
     * @param actor Usuário que editou a carga horária.
     * @return DTO da resposta contendo a matéria atualizada.
     */
    public SubjectResponseDTO editWorkLoad(Long id, Integer workLoad, User actor) {
        Subject subject = repository.findById(id).get();
        logsService.create(actor, subject, Collections.singletonList(new ChangeItem("workLoad", (Object) subject.getWorkLoad(), (Object) workLoad)), "update");
        subject.setWorkLoad(workLoad);
        return repository.save(subject).toDTO();
    }

    /**
     * Retorna uma lista paginada de matérias.
     *
     * @param pageable Informações de paginação.
     * @return Página de matérias na forma de DTOs de resposta.
     * @throws NaoEncontradoException se nenhuma matéria for encontrada.
     */
    public Page<SubjectResponseDTO> findSubjects(Pageable pageable) {
        try {
            return repository.findAll(pageable).map(Subject::toDTO);
        } catch (Exception e) {
            throw new NaoEncontradoException("Matéria nao encontrada");
        }
    }

    /**
     * Retorna uma lista paginada de professores associados a uma matéria.
     *
     * @param subjectId ID da matéria.
     * @param pageable Informações de paginação.
     * @return Página de professores associados à matéria.
     * @throws NaoEncontradoException se a matéria não for encontrada.
     */
    public Page<Teacher> findTeachersBySubject(Long subjectId, Pageable pageable) {
        Subject subject = repository.findById(subjectId)
                .orElseThrow(() -> new NaoEncontradoException("Matéria não encontrada"));
        List<Teacher> teachers = subject.getTeachers();
        return new PageImpl<>(teachers, pageable, teachers.size());
    }

    /**
     * Busca uma matéria pelo seu ID.
     *
     * @param id ID da matéria.
     * @return DTO da resposta contendo a matéria encontrada.
     * @throws NaoEncontradoException se a matéria não for encontrada.
     */
    public SubjectResponseDTO findSubjectById(Long id) {
        try {
            return repository.findById(id).get().toDTO();
        } catch (Exception e) {
            throw new NaoEncontradoException("Matéria nao encontrada");
        }
    }

    /**
     * Busca uma matéria pelo seu ID.
     *
     * @param id ID da matéria.
     * @return o objeto da materia em forma de {@link Subject}.
     * @throws NaoEncontradoException se a matéria nao for encontrada.
     * @see Subject
     * @author Gustavo Stinghen
     * @since 26/03/2025
     */
    public Subject getObjectSubject(Long id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            throw new NaoEncontradoException("Matéria nao encontrada");
        }
    }

    /**
     * Adiciona um professor a uma matéria.
     *
     * @param subject Matéria à qual o professor será adicionado.
     * @param teacher Professor a ser adicionado.
     * @param actor   Usuário que adicionou o professor.
     * @throws NaoEncontradoException se o professor não for encontrado.
     */
    public void addTeacherToSubject(Subject subject, Teacher teacher, User actor) {
        if(subject.addTeacher(teacher)) {
            logsService.create( actor, subject, Collections.singletonList( new AddItem("teachers", (Object) teacher ) ), "add" );
            repository.save(subject).toDTO();
        } else {
            throw new NaoEncontradoException("Professor nao encontrado");
        }
    }

    /**
     * Remove um professor de uma matéria.
     *
     * @param subject Matéria da qual o professor será removido.
     * @param teacher Professor a ser removido.
     * @param actor   Usuário que removeu o professor.
     * @throws NaoEncontradoException se o professor não for encontrado.
     */
    public void removeTeacherFromSubject(Subject subject, Teacher teacher, User actor) {
        if(subject.removeTeacher(teacher)) {
            logsService.create( actor, subject, Collections.singletonList( new AddItem("teachers", (Object) teacher ) ), "remove" );
            repository.save(subject).toDTO();
        } else {
            throw new NaoEncontradoException("Professor nao encontrado");
        }
    }

    /**
     * Deleta uma matéria pelo seu ID.
     *
     * @param id ID da matéria a ser deletada.
     * @param actor Usuário que deletou a matéria.
     * @throws NaoEncontradoException se a matéria não for encontrada.
     */
    public void delete(Long id, User actor) {
        try {
            logsService.create(actor, repository.findById(id).get(), "delete");
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NaoEncontradoException("Matéria nao encontrada");
        }
    }

    /**
     * Filtra matérias com base em um termo de busca.
     *
     * @param termo Termo utilizado para busca.
     * @param pageable Informações de paginação.
     * @return Página de matérias que correspondem ao termo de busca na forma de DTOs de resposta.
     */
    public Page<SubjectResponseDTO> subjectFilter(String termo, Pageable pageable) {
        return repository.findAll(SubjectSpecification.subjectFilter(termo), pageable).map(Subject::toDTO);
    }

    /**
     * Busca as matérias associadas a um professor específico.
     *
     * @param teacherId ID do professor.
     * @param pageable Informações de paginação.
     * @return Página de matérias associadas ao professor na forma de DTOs de resposta.
     * @throws NaoEncontradoException se o professor não for encontrado.
     */
    public Page<SubjectResponseDTO> findSubjectsByTeacher(Long teacherId, Pageable pageable) {

        Teacher teacher = teacherService.getObjectTeacher(teacherId);

        if (teacher == null) {
            throw new NaoEncontradoException("Professor nao encontrado");
        }

        List<Subject> subjects = teacher.getSubjects(); // Recupera as matérias associadas ao professor
        return new PageImpl<>(subjects, pageable, subjects.size()).map(Subject::toDTO);
    }

}
