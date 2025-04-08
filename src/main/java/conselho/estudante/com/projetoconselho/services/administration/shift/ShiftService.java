package conselho.estudante.com.projetoconselho.services.administration.shift;


import conselho.estudante.com.projetoconselho.models.dto.request.ADMINISTRATION.ShiftPostRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.administration.CourseResponseDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.administration.ShiftResponseDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.users.TeacherResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Course;
import conselho.estudante.com.projetoconselho.models.entity.administration.Shift;
import conselho.estudante.com.projetoconselho.models.entity.logs.AddItem;
import conselho.estudante.com.projetoconselho.models.entity.logs.ChangeItem;
import conselho.estudante.com.projetoconselho.models.entity.logs.EditableItem;
import conselho.estudante.com.projetoconselho.models.entity.users.Teacher;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.administration.ShiftRepository;
import conselho.estudante.com.projetoconselho.services.administration.CourseService;
import conselho.estudante.com.projetoconselho.services.logs.ShiftLogsService;
import conselho.estudante.com.projetoconselho.services.users.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


/**
 * Classe de serviço para a entidade {@link Shift}
 * @author Cauã Justimiano Dutra
 * @since 17/03/2025
 * @see Shift
 *
 * Atualizado em 20/03/2025
 * Conexão com o ShiftLogsService para gerar logs
 * @author Gustavo Stinghen
 * @see ShiftLogsService
 */

@Service
@AllArgsConstructor
public class ShiftService {

    private ShiftRepository repository;
    private ShiftLogsService logsService;
    private TeacherService teacherService;
    private CourseService courseService;

    /**
     * Adiciona um novo turno à aplicação.
     *
     * @param shiftPostRequestDTO DTO contendo os dados do novo turno.
     * @param actor Usuário que adicionou o turno.
     * @return DTO do turno adicionado.
     */
    public ShiftResponseDTO create(ShiftPostRequestDTO shiftPostRequestDTO, User actor) {

        try {
            Shift shift = shiftPostRequestDTO.toEntity();
            repository.save(shift);
            logsService.create(actor, shift, "create");
            return shift.toDTO();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adicionar turno: " + e.getMessage());
        }
    }

    /**
     * Retorna todos os turnos cadastrados, com paginação.
     *
     * @param pageable Objeto que contém informações de paginação (tamanho e número da página).
     * @return Página contendo os turnos.
     */
    public Page<ShiftResponseDTO> getAllShifts(Pageable pageable) {
        return repository.findAll(pageable).map(Shift::toDTO);
    }

    /**
     * Edita os dados de um turno existente.
     *
     * @param shiftPostRequestDTO DTO contendo os novos dados do turno.
     * @param id ID do turno a ser editado.
     * @return DTO do turno editado.
     * @throws NoSuchElementException Caso o turno não seja encontrado.
     */
    public ShiftResponseDTO update(ShiftPostRequestDTO shiftPostRequestDTO, Long id, User actor) {

        try {

            if ( this.searchShift(id) == null ) {
                throw new NaoEncontradoException("Turno nao encontrado");
            }

            Shift shift = shiftPostRequestDTO.toEntity();
            shift.setId(id);
            logsService.create(actor, shift, getEditableItems(repository.findById(id).get(), shift), "update");
            return repository.save(shift).toDTO();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar turno: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar para gerar logs que mostra os campos que foram editados
     * @param oldShift o turno antigo
     * @param shift o turno novo
     * @return uma lista com os campos editados
     */
    private List<EditableItem> getEditableItems(Shift oldShift, Shift shift) {

        List<EditableItem> changes = new ArrayList<>();

        if ( ! oldShift.getName().equals( shift.getName() ) ) {
            changes.add(new ChangeItem("name", (Object) oldShift.getName(), (Object) shift.getName()));
        }

        return changes;
    }

    /**
     * Edita o nome de um turno existente.
     *
     * @param shiftId ID do turno a ser editado.
     * @param newName Novo nome do turno.
     * @param actor Usuário que editou o turno.
     * @throws RuntimeException Caso o turno não seja encontrado.
     */
    public void editName(Long shiftId, String newName, User actor) {
        Shift shift = repository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Turno não encontrado"));
        String oldName = shift.getName();
        shift.setName(newName);
        logsService.create(actor, shift, Collections.singletonList(new ChangeItem("name", (Object) oldName, (Object) newName)), "update");
        repository.save(shift);
    }


    /**
     * Lista todos os professores de um turno, com paginação.
     *
     * @param shiftId ID do turno cujos professores devem ser listados.
     * @param pageable Objeto que contém informações de paginação (tamanho e número da página).
     * @return Página contendo os professores do turno, convertidos para DTO.
     * @throws RuntimeException Caso o turno não seja encontrado.
     */

    public Page<TeacherResponseDTO> listTeachersByShift(Long shiftId, Pageable pageable) {
        // Buscar o turno pelo ID
        return repository.findById(shiftId)
                .map(shift -> {
                    // Retornar os professores do turno, convertendo para DTO e paginando
                    return new PageImpl<>(
                            shift.getTeachers().stream() // Fluxo dos professores
                                    .map(Teacher::toDTO) // Convertendo cada professor para o DTO
                                    .collect(Collectors.toList()), // Coletando como lista
                            pageable, // Paginação
                            shift.getTeachers().size()); // Total de professores
                })
                .orElseThrow(() -> new RuntimeException("Turno não encontrado!"));
    }

    /**
     * Lista todos os cursos de um turno, com paginação.
     *
     * @param shiftId ID do turno cujos cursos devem ser listados.
     * @param pageable Objeto que contém informações de paginação (tamanho e número da página).
     * @return Página contendo os cursos do turno, convertidos para DTO.
     * @throws RuntimeException Caso o turno não seja encontrado.
     */

    public Page<CourseResponseDTO> listCourseByShift(Long shiftId, Pageable pageable) {
        Shift shift = repository.findById(shiftId).orElseThrow(() -> new RuntimeException("Turno nao encontrado"));
        return new PageImpl<>(
                shift.getCourses().stream() // Fluxo dos cursos
                        .map(Course::toDTO) // Convertendo cada curso para o DTO
                        .collect(Collectors.toList()), // Coletando como lista
                pageable, // Paginação
                shift.getCourses().size()); // Total de cursos
    }


    /**
     * Busca um turno pelo ID.
     *
     * @param id ID do turno a ser buscado.
     * @return DTO do turno encontrado.
     * @throws NoSuchElementException Caso o turno não seja encontrado.
     */
    public ShiftResponseDTO searchShift(Long id) {
        Shift shift = repository.findById(id).orElseThrow(NoSuchElementException::new);
        return shift.toDTO();
    }

    /**
     * Adiciona um professor a um turno.
     *
     * @param shiftId ID do turno ao qual o professor será adicionado.
     * @param teacherId ID do professor a ser adicionado.
     * @param actor Usuário que adicionou o professor.
     * @throws RuntimeException Caso o turno ou o professor não sejam encontrados, ou se o professor já estiver associado a este turno.
     */

    public void addTeacherToShift(Long shiftId, Long teacherId, User actor) {
        Shift shift = repository.findById(shiftId).orElseThrow(() -> new RuntimeException("Turno não encontrado"));

        Teacher teacher = teacherService.getObjectTeacher(teacherId);

        if ( teacher == null ) {
            throw new NaoEncontradoException("Professor nao encontrado");
        }

        if (shift.getTeachers().contains(teacher)) {
            throw new RuntimeException("Professor já está associado a este turno");
        }

        logsService.create( actor, shift, Collections.singletonList( new AddItem("teachers", (Object) teacher ) ), "add" );
        shift.getTeachers().add(teacher);
        repository.save(shift);
    }


    /**
     * Remove um professor de um turno.
     *
     * @param shiftId ID do turno do qual o professor será removido.
     * @param teacherId ID do professor a ser removido.
     * @param actor Usuário que removeu o professor.
     * @throws RuntimeException Caso o turno ou o professor não sejam encontrados, ou se o professor não estiver associado a este turno.
     */

    public void removeTeacherOfShift(Long shiftId, Long teacherId, User actor) {
        Shift shift = repository.findById(shiftId).orElseThrow(() -> new RuntimeException("Turno não encontrado"));

        Teacher teacher = teacherService.getObjectTeacher(teacherId);

        if ( teacher == null ) {
            throw new NaoEncontradoException("Professor nao encontrado");
        }

        if (!shift.getTeachers().contains(teacher)) {
            throw new RuntimeException("Professor não está associado a este turno");
        }

        logsService.create( actor, shift, Collections.singletonList( new AddItem("teachers", (Object) teacher ) ), "remove" );
        shift.getTeachers().remove(teacher);
        repository.save(shift);
    }


    /**
     * Adiciona um curso a um turno.
     *
     * @param shiftId ID do turno ao qual o curso será adicionado.
     * @param courseId ID do curso a ser adicionado.
     * @param actor Usuário que adicionou o curso.
     * @throws RuntimeException Caso o turno ou o curso não sejam encontrados, ou se o curso já estiver associado a este turno.
     */

    public void addCourseToShift(Long shiftId, Long courseId, User actor) {
        Shift shift = repository.findById(shiftId).orElseThrow(() -> new RuntimeException("Turno não encontrado"));

        Course course = courseService.getObjectCourse(courseId);

        if (shift.getCourses().contains(course)) {
            throw new RuntimeException("Curso já está associado a este turno");
        }

        logsService.create( actor, shift, Collections.singletonList( new AddItem("courses", (Object) course ) ), "add" );
        shift.getCourses().add(course);
        repository.save(shift);
    }


    /**
     * Remove um curso de um turno.
     *
     * @param shiftId ID do turno do qual o curso será removido.
     * @param courseId ID do curso a ser removido.
     * @param actor Usuário que removeu o curso.
     * @throws RuntimeException Caso o turno ou o curso não sejam encontrados, ou se o curso não estiver associado a este turno.
     */

    public void removeCourseOfShift(Long shiftId, Long courseId, User actor) {
        Shift shift = repository.findById(shiftId).orElseThrow(() -> new RuntimeException("Turno não encontrado"));
        Course course = courseService.getObjectCourse(courseId);

        if (course == null) {
            throw new NaoEncontradoException("Curso nao encontrado");
        }

        if (!shift.getCourses().contains(course)) {
            throw new RuntimeException("Curso não está associado a este turno");
        }

        logsService.create( actor, shift, Collections.singletonList( new AddItem("courses", (Object) course ) ), "remove" );

        shift.getCourses().remove(course);
        repository.save(shift);
    }

    /**
     * Busca turnos com base em uma pesquisa inteligente, considerando o nome do turno, nome do professor e nome do curso.
     *
     * @param text Texto a ser buscado.
     * @param page Número da página.
     * @param size Tamanho da página.
     * @return Página contendo os turnos filtrados.
     */
    public Page<Shift> searchShifts(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // Configura a paginação
        return repository.findAll(ShiftSpecification.smartSearch(text), pageable);
    }

    /**
     * Exclui um turno da aplicação.
     *
     * @param id ID do turno a ser excluído.
     * @param actor Usuário que está excluindo o turno.
     * @throws NoSuchElementException Caso o turno não seja encontrado.
     */
    public void deleteShift(Long id, User actor) {

        try {

            if ( searchShift(id) == null ) {
                throw new NaoEncontradoException("Turno nao encontrado");
            }

            logsService.create(actor, repository.findById(id).get(), "delete");
            repository.deleteById(id);

        } catch (Exception e) {
            throw new NaoEncontradoException("Turno nao encontrado");
        }

        repository.deleteById(id);
    }
}

