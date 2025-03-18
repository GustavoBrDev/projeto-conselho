package conselho.estudante.com.projetoconselho.SERVICES.USERS.SHIFT;


import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.ADMINISTRATION.ShiftPostRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ADMINISTRATION.ShiftResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Shift;
import conselho.estudante.com.projetoconselho.REPOSITORIES.ADMINISTRATION.ShiftRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;


@Service
@AllArgsConstructor
public class ShiftService {

    private ShiftRepository repository;

    /**
     * Adiciona um novo turno à aplicação.
     *
     * @param shiftPostRequestDTO DTO contendo os dados do novo turno.
     * @return DTO do turno adicionado.
     */
    public ShiftResponseDTO addShift(ShiftPostRequestDTO shiftPostRequestDTO) {
        Shift shift = repository.save(shiftPostRequestDTO.toEntity());
        return shift.toDTO();
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
    public ShiftResponseDTO editShift(ShiftPostRequestDTO shiftPostRequestDTO, Long id) {
        searchShift(id);
        Shift shift = shiftPostRequestDTO.toEntity();
        shift.setId(id);
        repository.save(shift);
        return shift.toDTO();
    }

    /**
     * Edita o nome de um turno existente.
     *
     * @param shiftId ID do turno a ser editado.
     * @param newName Novo nome do turno.
     * @throws RuntimeException Caso o turno não seja encontrado.
     */
    public void editName(Long shiftId, String newName) {
        Shift shift = repository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Turno não encontrado"));
        shift.setName(newName);
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
    /*
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
    */

//    METODO NA CONTROLLER:
//    @GetMapping("/{shiftId}/teachers")
//public ResponseEntity<Page<TeacherResponseDTO>> listarProfessoresPeloTurno(
//        @PathVariable Long shiftId, Pageable pageable) {
//    // Chama a service para listar os professores do turno
//    Page<TeacherResponseDTO> professores = shiftService.listarProfessoresPeloTurno(shiftId, pageable);
//    return ResponseEntity.ok(professores); // Retorna a página com os professores
//}


    /**
     * Lista todos os cursos de um turno, com paginação.
     *
     * @param shiftId ID do turno cujos cursos devem ser listados.
     * @param pageable Objeto que contém informações de paginação (tamanho e número da página).
     * @return Página contendo os cursos do turno, convertidos para DTO.
     * @throws RuntimeException Caso o turno não seja encontrado.
     */
    /*
    public Page<CourseResponseDTO> listCourseByShift(Long shiftId, Pageable pageable) {
        // Buscar o turno pelo ID
        return shiftRepository.findById(shiftId)
            .map(shift -> {
                // Retornar os cursos do turno, convertendo para DTO e paginando
                return new PageImpl<>(
                    shift.getCourse().stream() // Fluxo dos cursos
                        .map(Course::toDTO) // Convertendo cada curso para o DTO
                        .collect(Collectors.toList()), // Coletando como lista
                    pageable, // Paginação
                    shift.getCourse().size()); // Total de cursos
            })
            .orElseThrow(() -> new RuntimeException("Turno não encontrado!"));
    }
    */


/*
@GetMapping("/{shiftId}/courses")
public ResponseEntity<Page<CourseResponseDTO>> listarCursosPeloTurno(
        @PathVariable Long shiftId, Pageable pageable) {
    // Chama a service para listar os cursos do turno
    Page<CourseResponseDTO> cursos = shiftService.listarCursosPeloTurno(shiftId, pageable);
    return ResponseEntity.ok(cursos); // Retorna a página com os cursos
}
 */

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
     * @throws RuntimeException Caso o turno ou o professor não sejam encontrados, ou se o professor já estiver associado a este turno.
     */
    /*
    public void addTeacherToShift(Long shiftId, Long teacherId) {
        Shift shift = repository.findById(shiftId).orElseThrow(() -> new RuntimeException("Turno não encontrado"));

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        if (shift.getTeachers().contains(teacher)) {
            throw new RuntimeException("Professor já está associado a este turno");
        }

        shift.getTeachers().add(teacher);
        shiftRepository.save(shift);
    }
    */

    /**
     * Remove um professor de um turno.
     *
     * @param shiftId ID do turno do qual o professor será removido.
     * @param teacherId ID do professor a ser removido.
     * @throws RuntimeException Caso o turno ou o professor não sejam encontrados, ou se o professor não estiver associado a este turno.
     */
    /*
    public void removeTeacherOfShift(Long shiftId, Long teacherId) {
        Shift shift = repository.findById(shiftId).orElseThrow(() -> new RuntimeException("Turno não encontrado"));
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        if (!shift.getTeachers().contains(teacher)) {
            throw new RuntimeException("Professor não está associado a este turno");
        }

        shift.getTeachers().remove(teacher);
        shiftRepository.save(shift);
    }
    */

    /**
     * Adiciona um curso a um turno.
     *
     * @param shiftId ID do turno ao qual o curso será adicionado.
     * @param courseId ID do curso a ser adicionado.
     * @throws RuntimeException Caso o turno ou o curso não sejam encontrados, ou se o curso já estiver associado a este turno.
     */
    /*
    public void addCourseToShift(Long shiftId, Long courseId) {
        Shift shift = repository.findById(shiftId).orElseThrow(() -> new RuntimeException("Turno não encontrado"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        if (shift.getCourse().contains(course)) {
            throw new RuntimeException("Curso já está associado a este turno");
        }

        shift.getCourse().add(course);
        shiftRepository.save(shift);
    }
    */

/**
 * Remove um curso de um turno.
 *
 * @param shiftId ID do turno do qual o curso será removido.
 * @param courseId ID do curso a ser removido.
 * @throws RuntimeException Caso o turno ou o curso não sejam encontrados, ou se o curso não estiver associado a este turno.
 */
    /*
    public void removeCourseOfShift(Long shiftId, Long courseId) {
        Shift shift = repository.findById(shiftId).orElseThrow(() -> new RuntimeException("Turno não encontrado"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        if (!shift.getCourse().contains(course)) {
            throw new RuntimeException("Curso não está associado a este turno");
        }

        shift.getCourse().remove(course);
        shiftRepository.save(shift);
    }
    */

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
     * @throws NoSuchElementException Caso o turno não seja encontrado.
     */
    public void deleteShift(Long id) {
        searchShift(id);
        repository.deleteById(id);
    }
}

