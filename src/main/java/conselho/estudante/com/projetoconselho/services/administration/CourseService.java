package conselho.estudante.com.projetoconselho.services.administration;

import conselho.estudante.com.projetoconselho.models.dto.request.administration.CourseRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.administration.CourseResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Classe;
import conselho.estudante.com.projetoconselho.models.entity.administration.Course;
import conselho.estudante.com.projetoconselho.models.entity.administration.Shift;
import conselho.estudante.com.projetoconselho.models.entity.administration.Subject;
import conselho.estudante.com.projetoconselho.models.entity.logs.AddItem;
import conselho.estudante.com.projetoconselho.models.entity.logs.ChangeItem;
import conselho.estudante.com.projetoconselho.models.entity.logs.EditableItem;
import conselho.estudante.com.projetoconselho.models.entity.users.Supervisor;
import conselho.estudante.com.projetoconselho.models.entity.users.Teacher;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.models.exceptions.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.administration.CourseRepository;
import conselho.estudante.com.projetoconselho.services.administration.shift.ShiftService;
import conselho.estudante.com.projetoconselho.services.logs.CourseLogsService;
import conselho.estudante.com.projetoconselho.services.users.SupervisorService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Serviço para gerenciar operações relacionadas à entidade {@link Course}.
 *
 * @author Joana Voigt
 * @since 19/03/2025
 *
 * @see Course
 * @see CourseRequestDTO
 * @see CourseResponseDTO
 *
 * Atualizado em 24/03/2025
 * Conexão com o CourseLogsService para gerar logs
 * @author Gustavo Stinghen
 * @see CourseLogsService
 */
@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class CourseService {

    private CourseRepository repository;
    @Lazy
    private SupervisorService supervisorService;
    private CourseLogsService logsService;
    private ShiftService shiftService;

    /**
     * Cria um novo curso com os dados fornecidos.
     *
     * @param courseRequestDTO Objeto contendo os dados do curso a ser criado.
     * @param actor O usuário que criou o curso.
     * @return O curso criado convertido para DTO.
     * @throws DadosDuplicadosException Se um curso com o mesmo nome já existir.
     */
    public CourseResponseDTO create(CourseRequestDTO courseRequestDTO, User actor) {
        Course course = courseRequestDTO.convert();
        if (repository.existsByName(course.getName())) {
            throw new DadosDuplicadosException("Curso ja cadastrado");
        } else {
            shiftService.addCourseToShift( course.getShift().getId(), course.getId(), actor);
            logsService.create(actor, course, "create");
            return repository.save(course).toDTO();
        }
    }

    /**
     * Atualiza um curso existente com novos dados.
     *
     * @param id ID do curso a ser atualizado.
     * @param courseRequestDTO Dados atualizados do curso.
     * @param actor O usuário que atualizou o curso.
     * @return O curso atualizado convertido para DTO.
     * @throws DadosDuplicadosException Se um curso com o mesmo nome já existir.
     * @throws NaoEncontradoException Se o curso não for encontrado.
     */
    public CourseResponseDTO update(Long id, CourseRequestDTO courseRequestDTO, User actor) {
        Course course = courseRequestDTO.convert();
        if (repository.existsById(id)) {
            course.setId(id);
            if (repository.existsByName(course.getName())) {
                throw new DadosDuplicadosException("Curso ja cadastrado");
            } else {

                logsService.create(actor, course, getEditableItems(repository.findById(id).get(), course, actor), "update");
                return repository.save(course).toDTO();
            }
        }
        throw new NaoEncontradoException("Curso nao encontrado");
    }

    /**
    * Método auxiliar para gerar logs que mostra os campos que foram editados
     * @param oldCourse o curso antigo
     * @param course o curso novo
     * @return uma lista com os campos editados
     */
    private List<EditableItem> getEditableItems(Course oldCourse, Course course, User actor) {
        List<EditableItem> changes = new ArrayList<>();

        if (!oldCourse.getName().equals(course.getName())) {
            changes.add(new ChangeItem("name", (Object) oldCourse.getName(), (Object) course.getName()));
        }

        if (!oldCourse.getVisualIdentity().equals(course.getVisualIdentity())) {
            changes.add(new ChangeItem("visualIdentity", (Object) oldCourse.getVisualIdentity(), (Object) course.getVisualIdentity()));
        }

        if (!oldCourse.getWorkLoad().equals(course.getWorkLoad())) {
            changes.add(new ChangeItem("workLoad", (Object) oldCourse.getWorkLoad(), (Object) course.getWorkLoad()));
        }

        if ( ! oldCourse.getLevel().equals( course.getLevel() ) ) {
            changes.add(new ChangeItem("level", (Object) oldCourse.getLevel(), (Object) course.getLevel()));
        }

        if ( ! oldCourse.getShift() .equals( course.getShift() ) ) {
            shiftService.removeCourseOfShift( oldCourse.getShift().getId(), oldCourse.getId(), actor);
            shiftService.addCourseToShift( course.getShift().getId(), course.getId(), actor);
            changes.add(new ChangeItem("shift", (Object) oldCourse.getShift(), (Object) course.getShift()));
        }

        if ( ! oldCourse.getSupervisor().equals( course.getSupervisor() ) ) {
            changes.add(new ChangeItem("supervisor", (Object) oldCourse.getSupervisor(), (Object) course.getSupervisor()));
        }

        return changes;
    }

    /**
     * Edita o nome de um curso específico.
     *
     * @param id ID do curso.
     * @param name Novo nome.
     * @param actor O usuário que editou o curso.
     * @return DTO da resposta contendo o curso atualizado.
     */
    public CourseResponseDTO editName(Long id, String name, User actor) {
        Course course = repository.findById(id).get();
        String oldName = course.getName();
        course.setName(name);
        logsService.create(actor, course, Collections.singletonList(new ChangeItem("name", (Object) oldName, (Object) name)), "update");
        return repository.save(course).toDTO();
    }

    /**
     * Edita a identidade visual de um curso específico.
     *
     * @param id ID do curso.
     * @param visualIdentity Nova identidade visual.
     * @param actor O usuário que editou o curso.
     * @return O curso atualizado convertido para DTO.
     */
    public CourseResponseDTO editVisualIdentity(Long id, String visualIdentity, User actor) {
        Course course = repository.findById(id).get();
        String oldVisualIdentity = course.getVisualIdentity();
        course.setVisualIdentity(visualIdentity);
        logsService.create(actor, course, Collections.singletonList(new ChangeItem("visualIdentity", (Object) oldVisualIdentity, (Object) visualIdentity)), "update");
        return repository.save(course).toDTO();
    }

    /**
     * Edita a carga horária de um curso específico.
     *
     * @param id o ID do curso
     * @param workLoad a nova carga horária
     * @param actor o usuário que editou o curso
     * @return O curso atualizado convertido para DTO.
     */
    public CourseResponseDTO editWorkLoad(Long id, Integer workLoad, User actor) {
        Course course = repository.findById(id).get();
        Integer oldWorkLoad = course.getWorkLoad();
        course.setWorkLoad(workLoad);
        logsService.create(actor, course, Collections.singletonList(new ChangeItem("workLoad", (Object) oldWorkLoad, (Object) workLoad)), "update");
        return repository.save(course).toDTO();
    }

    /**
     * Edita o nivel de um curso específico.
     *
     * @param id o ID do curso
     * @param level o novo nivel
     * @param actor o usuário que editou o curso
     * @return O curso atualizado convertido para DTO.
     */
    public CourseResponseDTO editLevel(Long id, String level, User actor) {
        Course course = repository.findById(id).get();
        String oldLevel = course.getLevel();
        course.setLevel(level);
        logsService.create(actor, course, Collections.singletonList(new ChangeItem("level", (Object) oldLevel, (Object) level)), "update");
        return repository.save(course).toDTO();
    }

    /**
     * Edita o supervisor de um curso específico.
     *
     * @param id o ID do curso
     * @param supervisorId o ID do supervisor
     * @param actor o usuário que editou o curso
     * @return O curso atualizado convertido para DTO.
     */
    public CourseResponseDTO editSupervisor(Long id, Long supervisorId, User actor) {
        if (repository.findById(id).isEmpty()) {
            throw  new RuntimeException("Curso não encontrado");
        }
        if ( supervisorService.findById(supervisorId) == null) {
            throw  new RuntimeException("Supervisor não encontrado");
        }
        Course course = repository.findById(id).get();
        supervisorService.removeCourse(course.getSupervisor().getId(), course, actor);
        supervisorService.addCourse(supervisorId, course, actor);
        Supervisor supervisor = supervisorService.findObjectSupervisor(supervisorId);
        logsService.create(actor, course, Collections.singletonList(new ChangeItem("supervisor", (Object) course.getSupervisor(), (Object) supervisor)), "update");
        course.setSupervisor(supervisor);
        return repository.save(course).toDTO();
    }

    /**
     * Edita o turno de um curso específico.
     *
     * @param id o ID do curso
     * @param shift o novo turno
     * @param actor o usuário que editou o curso
     * @return O curso atualizado convertido para DTO.
     */
    public CourseResponseDTO editShift(Long id, Shift shift, User actor) {
        Course course = repository.findById(id).get();
        shiftService.removeCourseOfShift(course.getShift().getId(), course.getId(), actor);
        shiftService.addCourseToShift(shift.getId(), course.getId(), actor);
        logsService.create(actor, course, Collections.singletonList(new ChangeItem("shift", (Object) course.getShift(), (Object) shift)), "update");
        course.setShift(shift);
        return repository.save(course).toDTO();
    }

    /**
     * Retorna uma lista paginada de cursos.
     *
     * @param pageable Informações de paginação.
     * @return Página de cursos na forma de DTOs de resposta.
     */
    public Page<CourseResponseDTO> findCourses(Pageable pageable) {
        try{
            return repository.findAll(pageable).map(Course::toDTO);
        } catch (Exception e){
            throw new NaoEncontradoException("Curso nao encontrado");
        }
    }

    /**
     * Busca os professores associados a um curso específico.
     *
     * @param courseId ID do curso.
     * @param pageable Informações de paginação.
     * @return Página de professores associados ao curso.
     * @throws NaoEncontradoException Se o curso não for encontrado.
     */
    public Page<Teacher> findTeacherByCourse(Long courseId, Pageable pageable) {
        Course course = repository.findById(courseId)
                .orElseThrow(() -> new NaoEncontradoException("Curso nao encontrado"));
        List<Teacher> teachers = course.getTeachers();
        return new PageImpl<>(teachers, pageable, teachers.size());
    }

    /**
     * Busca as matérias associadas a um curso específico.
     *
     * @param courseId ID do curso.
     * @param pageable Informações de paginação.
     * @return Página de matérias associadas ao curso.
     * @throws NaoEncontradoException Se o curso não for encontrado.
     */
    public Page<Subject> findSubjectByCourse(Long courseId, Pageable pageable) {
        Course course = repository.findById(courseId)
                .orElseThrow(() -> new NaoEncontradoException("Curso nao encontrado"));
        List<Subject> subjects = course.getSubjects();
        return new PageImpl<>(subjects, pageable, subjects.size());
    }

    /**
     *  Adiciona um professor ao curso.
     *
     * @param course o curso ao qual o professor sera adicionado
     * @param teacher o professor a ser adicionado
     * @param actor o usuario que adicionou o professor
     * @return o curso atualizado convertido para DTO
     */
    public CourseResponseDTO addTeacherToCourse(Course course, Teacher teacher, User actor) {
        if(course.addTeacher(teacher)){
            logsService.create( actor, course, Collections.singletonList( new AddItem("teachers", (Object) teacher ) ), "add" );
            return repository.save(course).toDTO();
        } else {
            throw new NaoEncontradoException("Professor não encontrado");
        }
    }

    /**
     * Adiciona uma matéria ao curso.
     *
     * @param course O curso ao qual a matéria será adicionada.
     * @param subject A matéria a ser adicionada ao curso.
     * @param actor O usuário que adicionou a matéria ao curso.
     * @return DTO contendo os detalhes do curso atualizado.
     * @throws NaoEncontradoException Se a matéria não for encontrada ou não puder ser adicionada.
     */
    public CourseResponseDTO addSubjectToCourse(Course course, Subject subject, User actor) {
        if(course.addSubject(subject)){
            logsService.create( actor, course, Collections.singletonList( new AddItem("subjects", (Object) subject ) ), "add" );
            return repository.save(course).toDTO();
        } else {
            throw new NaoEncontradoException("Materia não encontrada");
        }
    }

    /**
     * Adiciona uma classe ao curso.
     *
     * @param course o curso ao qual a classe sera adicionada
     * @param classe a classe a ser adicionada
     * @param actor o usuario que adicionou a classe
     * @return DTO contendo os detalhes do curso atualizado.
     * @throws NaoEncontradoException Se a classe nao for encontrada ou nao puder ser adicionada.
     * @author Gustavo Stinghen
     * @since 25/03/2024
     */
    public CourseResponseDTO addClassToCourse(Course course, Classe classe, User actor) {
        if(course.addClasse(classe)){
            logsService.create( actor, course, Collections.singletonList( new AddItem("classes", (Object) classe ) ), "add" );
            return repository.save(course).toDTO();
        } else {
            throw new NaoEncontradoException("Materia nao encontrada");
        }
    }

    /**
     *  Remove um professor ao curso.
     *
     * @param course o curso ao qual o professor sera removido
     * @param teacher o professor a ser removido
     * @param actor o usuario que removeu o professor
     * @return DTO contendo os detalhes do curso atualizado.
     * @throws NaoEncontradoException Se a matéria não for encontrada ou não puder ser adicionada.
     */
    public CourseResponseDTO removeTeacherFromCourse(Course course, Teacher teacher, User actor) {
        if(course.removeTeacher(teacher)){
            logsService.create( actor, course, Collections.singletonList( new AddItem("teachers", (Object) teacher ) ), "remove" );
            return repository.save(course).toDTO();
        } else {
            throw new NaoEncontradoException("Professor nao encontrado");
        }
    }

    /**
     * Remove uma materia ao curso.
     *
     * @param course o curso ao qual a materia sera removida
     * @param subject a materia a ser removida
     * @param actor o usuario que removeu a materia
     * @return DTO contendo os detalhes do curso atualizado.
     * @throws NaoEncontradoException Se a matéria nao for encontrada ou nao puder ser adicionada.
     */
    public CourseResponseDTO removeSubjectFromCourse(Course course, Subject subject, User actor) {
        if(course.removeSubject(subject)){
            logsService.create( actor, course, Collections.singletonList( new AddItem("subjects", (Object) subject ) ), "remove" );
            return repository.save(course).toDTO();
        } else {
            throw new NaoEncontradoException("Materia nao encontrada");
        }
    }

    /**
     * Remove uma classe ao curso.
     *
     * @param course o curso ao qual a classe sera removida
     * @param classe a classe a ser removida
     * @param actor o usuario que removeu a classe
     * @return DTO contendo os detalhes do curso atualizado.
     * @throws NaoEncontradoException Se a classe nao for encontrada ou nao puder ser adicionada.
     * @author Gustavo Stinghen
     * @since 25/03/2024
     */
    public CourseResponseDTO removeClassFromCourse(Course course, Classe classe, User actor) {
        if(course.removeClasse(classe)){
            logsService.create( actor, course, Collections.singletonList( new AddItem("classes", (Object) classe ) ), "remove" );
            return repository.save(course).toDTO();
        } else {
            throw new NaoEncontradoException("Materia nao encontrada");
        }
    }

    /**
     * Busca um curso pelo seu ID.
     *
     * @param id O ID do curso a ser buscado.
     * @return DTO contendo os detalhes do curso encontrado.
     * @throws NaoEncontradoException Se o curso não for encontrado.
     */
    public CourseResponseDTO findCourseById(Long id) {
        try {
            return repository.findById(id).get().toDTO();
        } catch (Exception e) {
            throw new NaoEncontradoException("Curso nao encontrado");
        }
    }

    /**
     * Deleta um curso pelo seu ID.
     *
     * @param id ID do curso a ser deletado.
     * @param actor Usuário que deletou o curso.
     * @throws NaoEncontradoException Se o curso não for encontrado.
     */
    public void delete(Long id, User actor) {
        try {
            logsService.create( actor, repository.findById(id).get(), "delete" );
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NaoEncontradoException("Curso nao encontrado");
        }
    }

    /**
     * Busca um curso pelo seu ID.
     *
     * @param id O ID do curso a ser buscado.
     * @return O curso encontrado.
     * @throws NaoEncontradoException Se o curso nao for encontrado.
     * @author Gustavo Stinghen
     * @since 24/03/2024
     */
    public Course getObjectCourse ( Long id) {
        return repository.findById(id).orElseThrow(() -> new NaoEncontradoException("Curso nao encontrado"));
    }


}
