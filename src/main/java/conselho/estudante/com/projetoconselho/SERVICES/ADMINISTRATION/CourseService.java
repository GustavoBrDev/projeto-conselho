package conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.ADMINISTRATION.CourseRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ADMINISTRATION.CourseResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Course;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Shift;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Subject;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Supervisor;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.ADMINISTRATION.CourseRepository;
import conselho.estudante.com.projetoconselho.REPOSITORIES.USERS.SupervisorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
 */
@Service
@AllArgsConstructor
public class CourseService {

    private CourseRepository repository;
    private SupervisorRepository supervisorRepository;

    /**
     * Cria um novo curso com os dados fornecidos.
     *
     * @param courseRequestDTO Objeto contendo os dados do curso a ser criado.
     * @return O curso criado convertido para DTO.
     * @throws DadosDuplicadosException Se um curso com o mesmo nome já existir.
     */
    public CourseResponseDTO create(CourseRequestDTO courseRequestDTO) {
        Course course = courseRequestDTO.convert();
        if (repository.existsByName(course.getName())) {
            throw new DadosDuplicadosException("Curso ja cadastrado");
        } else {
            return repository.save(course).toDTO();
        }
    }

    /**
     * Atualiza um curso existente com novos dados.
     *
     * @param id ID do curso a ser atualizado.
     * @param courseRequestDTO Dados atualizados do curso.
     * @return O curso atualizado convertido para DTO.
     * @throws DadosDuplicadosException Se um curso com o mesmo nome já existir.
     * @throws NaoEncontradoException Se o curso não for encontrado.
     */
    public CourseResponseDTO update(Long id, CourseRequestDTO courseRequestDTO) {
        Course course = courseRequestDTO.convert();
        if (repository.existsById(id)) {
            course.setId(id);
            if (repository.existsByName(course.getName())) {
                throw new DadosDuplicadosException("Curso ja cadastrado");
            } else {
                return repository.save(course).toDTO();
            }
        }
        throw new NaoEncontradoException("Curso nao encontrado");
    }

    /**
     * Edita o nome de um curso específico.
     *
     * @param id ID do curso.
     * @param name Novo nome.
     * @return DTO da resposta contendo o curso atualizado.
     */
    public CourseResponseDTO editName(Long id, String name) {
        Course course = repository.findById(id).get();
        course.setName(name);
        return repository.save(course).toDTO();
    }

    /**
     * Edita a identidade visual de um curso específico.
     *
     * @param id ID do curso.
     * @param visualIdentity Nova identidade visual.
     * @return O curso atualizado convertido para DTO.
     */
    public CourseResponseDTO editVisualIdentity(Long id, String visualIdentity) {
        Course course = repository.findById(id).get();
        course.setVisualIdentity(visualIdentity);
        return repository.save(course).toDTO();
    }

    /**
     * Edita a carga horária de um curso específico.
     *
     * @param id
     * @param workLoad
     * @return O curso atualizado convertido para DTO.
     */
    public CourseResponseDTO editWorkLoad(Long id, Integer workLoad) {
        Course course = repository.findById(id).get();
        course.setWorkLoad(workLoad);
        return repository.save(course).toDTO();
    }

    /**
     * Edita o nivel de um curso específico.
     *
     * @param id
     * @param level
     * @return O curso atualizado convertido para DTO.
     */
    public CourseResponseDTO editLevel(Long id, String level) {
        Course course = repository.findById(id).get();
        course.setLevel(level);
        return repository.save(course).toDTO();
    }

    /**
     * Edita o supervisor de um curso específico.
     *
     * @param id
     * @param supervisorId
     * @return O curso atualizado convertido para DTO.
     */
    public CourseResponseDTO editSupervisor(Long id, Long supervisorId) {
        if (!repository.findById(id).isPresent()) {
            throw  new RuntimeException("Curso não encontrado");
        }
        if (!supervisorRepository.findById(supervisorId).isPresent()) {
            throw  new RuntimeException("Supervisor não encontrado");
        }
        Course course = repository.findById(id).get();
        Supervisor supervisor = supervisorRepository.findById(supervisorId).get();
        course.setSupervisor(supervisor);
        return repository.save(course).toDTO();
    }

    /**
     * Edita o turno de um curso específico.
     *
     * @param id
     * @param shift
     * @return O curso atualizado convertido para DTO.
     */
    public CourseResponseDTO editShift(Long id, Shift shift) {
        Course course = repository.findById(id).get();
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
     * @param course
     * @param teacher
     * @return
     */
    public CourseResponseDTO addTeacherToCourse(Course course, Teacher teacher) {
        if(course.addTeacher(teacher)){
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
     * @return DTO contendo os detalhes do curso atualizado.
     * @throws NaoEncontradoException Se a matéria não for encontrada ou não puder ser adicionada.
     */
    public CourseResponseDTO addSubjectToCourse(Course course, Subject subject) {
        if(course.addSubject(subject)){
            return repository.save(course).toDTO();
        } else {
            throw new NaoEncontradoException("Materia não encontrada");
        }
    }

    /**
     *  Remove um professor ao curso.
     *
     * @param course
     * @param teacher
     * @return DTO contendo os detalhes do curso atualizado.
     * @throws NaoEncontradoException Se a matéria não for encontrada ou não puder ser adicionada.
     */
    public CourseResponseDTO removeTeacherFromCourse(Course course, Teacher teacher) {
        if(course.removeTeacher(teacher)){
            return repository.save(course).toDTO();
        } else {
            throw new NaoEncontradoException("Professor nao encontrado");
        }
    }

    /**
     * Remove uma materia ao curso.
     *
     * @param course
     * @param subject
     * @return DTO contendo os detalhes do curso atualizado.
     * @throws NaoEncontradoException Se a matéria nao for encontrada ou nao puder ser adicionada.
     */
    public CourseResponseDTO removeSubjectFromCourse(Course course, Subject subject) {
        if(course.removeSubject(subject)){
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
     * @throws NaoEncontradoException Se o curso não for encontrado.
     */
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NaoEncontradoException("Curso nao encontrado");
        }
    }

}
