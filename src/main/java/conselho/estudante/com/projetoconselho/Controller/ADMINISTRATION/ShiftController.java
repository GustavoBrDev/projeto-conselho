package conselho.estudante.com.projetoconselho.Controller.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.ADMINISTRATION.ShiftPostRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ADMINISTRATION.CourseResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ADMINISTRATION.ShiftResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.TeacherResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION.SHIFT.ShiftService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Classe controladora para gerenciar as operações dos turnos ({@link Shift}).
 *
 * @author joana voigt
 * @since 24/03/2025
 *
 * @see ShiftService
 */
@RestController
@RequestMapping("/administration/shift")
@AllArgsConstructor
public class ShiftController {
    private ShiftService service;

    /**
     * Adiciona um novo turno.
     *
     * @param shiftPostRequestDTO DTO contendo os dados do novo turno.
     * @param actor Usuário que está criando o turno.
     * @return ResponseEntity com o DTO do turno criado.
     */
    @PostMapping
    public ResponseEntity<ShiftResponseDTO> postShift( @RequestBody @Valid ShiftPostRequestDTO shiftPostRequestDTO, User actor) {
        try {
            return new ResponseEntity<>(service.create(shiftPostRequestDTO, actor), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retorna todos os turnos cadastrados, com paginação.
     *
     * @param pageable Objeto que contém informações de paginação (tamanho, número da página e ordenação).
     * @return ResponseEntity com uma página contendo os turnos.
     */
    @GetMapping("/allShifts")
        public ResponseEntity<Page<ShiftResponseDTO>> getAllShifts(
            @PageableDefault(
                        page = 0,
                        size = 20,
                        sort = "id",
                        direction = Sort.Direction.ASC
                )
                Pageable pageable) {
        try {
            return new ResponseEntity<>(service.getAllShifts(pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Atualiza um turno existente.
     *
     * @param shiftPostRequestDTO DTO com os novos dados do turno.
     * @param id ID do turno a ser atualizado.
     * @param actor Usuário que está atualizando o turno.
     * @return ResponseEntity com o DTO do turno atualizado.
     */
    @PutMapping("/{id}")
        public ResponseEntity<ShiftResponseDTO> putShift(@RequestBody @Valid ShiftPostRequestDTO shiftPostRequestDTO, @PathVariable int id, User actor) {
            try {
                return new ResponseEntity<>(service.update(shiftPostRequestDTO, id, actor), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

    /**
     * Edita o nome de um turno existente.
     *
     * @param id ID do turno.
     * @param name Novo nome do turno.
     * @return ResponseEntity com status OK em caso de sucesso.
     */
    @PutMapping("/editName/{id}")
    public ResponseEntity<String> editName(@PathVariable Long id, @RequestParam String name) {
        try {
            service.editName(id, name);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retorna todos os professores de um turno específico, com paginação.
     *
     * @param shiftId ID do turno.
     * @param pageable Objeto de paginação.
     * @return ResponseEntity com uma página contendo os professores.
     */
    @GetMapping("/teachers/{id}")
    public ResponseEntity<Page<TeacherResponseDTO>> listarProfessoresPeloTurno(
            @PathVariable Long shiftId, Pageable pageable) {
        Page<TeacherResponseDTO> professores = service.listTeachersByShift(shiftId, pageable);
        return ResponseEntity.ok(professores);
    }

    /**
     * Retorna todos os cursos de um turno específico, com paginação.
     *
     * @param shiftId ID do turno.
     * @param pageable Objeto de paginação.
     * @return ResponseEntity com uma página contendo os cursos.
     */
    @GetMapping("/courses/{id}")
    public ResponseEntity<Page<CourseResponseDTO>> listarCursosPeloTurno(
            @PathVariable Long shiftId, Pageable pageable) {
        Page<CourseResponseDTO> cursos = service.listCourseByShift(shiftId, pageable);
        return ResponseEntity.ok(cursos);
    }

    /**
     * Retorna um turno específico pelo ID.
     *
     * @param id ID do turno a ser buscado.
     * @return ResponseEntity com o DTO do turno encontrado.
     */
    @GetMapping("/{id}")
        public ResponseEntity<ShiftResponseDTO> getShiftById(@PathVariable Long id) {
            try {
                return new ResponseEntity<>(service.searchShift(id), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

    /**
     * Associa um professor a um turno.
     *
     * @param shiftId ID do turno.
     * @param teacherId ID do professor.
     * @return ResponseEntity com status OK em caso de sucesso.
     */
    @PostMapping("/teachers/{shiftId}/{teacherId}")
    public ResponseEntity<String> addTeacherToShift(@PathVariable Long shiftId, @PathVariable Long teacherId) {
        try {
            service.addTeacherToShift(shiftId, teacherId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Remove um professor de um turno.
     *
     * @param shiftId ID do turno.
     * @param teacherId ID do professor.
     * @return ResponseEntity com status OK em caso de sucesso.
     */
    @DeleteMapping("/teachers/{shiftId}/{teacherId}")
    public ResponseEntity<String> removeTeacherOfShift(@PathVariable Long shiftId, @PathVariable Long teacherId) {
        try {
            service.removeTeacherOfShift(shiftId, teacherId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Associa um curso a um turno.
     *
     * @param shiftId ID do turno.
     * @param courseId ID do curso.
     * @return ResponseEntity com status OK em caso de sucesso.
     */
    @PostMapping("/teachers/{shiftId}/{courseId}")
    public ResponseEntity<String> addCourseToShift(@PathVariable Long shiftId, @PathVariable Long courseId) {
        try {
            service.addCourseToShift(shiftId, courseId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Remove um curso de um turno.
     *
     * @param shiftId ID do turno.
     * @param courseId ID do curso.
     * @return ResponseEntity com status OK em caso de sucesso.
     */
    @DeleteMapping("/teachers/{shiftId}/{courseId}")
    public ResponseEntity<String> removeCourseOfShift(@PathVariable Long shiftId, @PathVariable Long courseId) {
        try {
            service.removeCourseOfShift(shiftId, courseId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Exclui um turno da aplicação.
     *
     * @param id ID do turno a ser excluído.
     * @param actor Usuário que está excluindo o turno.
     * @return ResponseEntity com status OK em caso de sucesso.
     */
    @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteShift(@PathVariable Long id, User actor) {
            try {
                service.deleteShift(id, actor);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
    }
}


