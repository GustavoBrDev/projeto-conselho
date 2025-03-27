package conselho.estudante.com.projetoconselho.Controller.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.ADMINISTRATION.ShiftPostRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ADMINISTRATION.CourseResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ADMINISTRATION.ShiftResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.TeacherResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION.SHIFT.ShiftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
 * Classe de controller da entidade Shift
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

    @Tag( name = "Shift", description = "Recurso para gerenciamento de turnos" )
    @Operation(summary = "Cria um turno", description = "Cria um turno e retorna o turno criado com o status HTTP 201" )
    @ApiResponse(responseCode = "201", description = "Turno criado com sucesso",
            content = @Content(schema = @Schema(implementation = ShiftResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Turno 1\",  \"createdAt\": \"2023-01-01\", \"teacher\": 1 \"course\": 1}")))
    @ApiResponse(responseCode = "400", description = "Erro ao criar turno")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PostMapping
    public ResponseEntity<ShiftResponseDTO> postShift( @RequestBody @Valid ShiftPostRequestDTO shiftPostRequestDTO, User actor) {
        try {
            return new ResponseEntity<>(service.create(shiftPostRequestDTO, actor), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Tag( name = "Shift", description = "Recurso para gerenciamento de turnos" )
    @Operation(summary = "Busca todos os turnos", description = "Busca todos os turnos e retorna com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Turnos encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = ShiftResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Turno 1\",  \"createdAt\": \"2023-01-01\", \"teacher\": 1 \"course\": 1}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar turnos")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
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

    @Tag( name = "Shift", description = "Recurso para gerenciamento de turnos" )
    @Operation(summary = "Edita um turno", description = "Edita um turno e retorna o turno editado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Turno editado com sucesso",
            content = @Content(schema = @Schema(implementation = ShiftResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Turno 1\",  \"createdAt\": \"2023-01-01\", \"teacher\": 1 \"course\": 1}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar turno")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PutMapping("/{id}")
        public ResponseEntity<ShiftResponseDTO> putShift(@RequestBody @Valid ShiftPostRequestDTO shiftPostRequestDTO, @PathVariable Long id, User actor) {
            try {
                return new ResponseEntity<>(service.update(shiftPostRequestDTO, id, actor), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

    @Tag( name = "Shift", description = "Recurso para gerenciamento de turnos" )
    @Operation(summary = "Edita o nome de um turno", description = "Edita o nome de um turno e retorna o turno editado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Turno editado com sucesso",
            content = @Content(schema = @Schema(implementation = ShiftResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Turno 1\",  \"createdAt\": \"2023-01-01\", \"teacher\": 1 \"course\": 1}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar turno")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PutMapping("/editName/{id}")
    public ResponseEntity<String> editName(@PathVariable Long id, @RequestParam String name, @RequestParam User actor) {
        try {
            service.editName(id, name, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Tag( name = "Shift", description = "Recurso para gerenciamento de turnos" )
    @Operation(summary = "Busca todos os professores de um turno", description = "Busca todos os professores de um turno e retorna com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Professores encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = TeacherResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Turno 1\",  \"createdAt\": \"2023-01-01\", \"teacher\": 1 \"course\": 1}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar professores")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/teachers/{id}")
    public ResponseEntity<Page<TeacherResponseDTO>> listarProfessoresPeloTurno(
            @PathVariable Long shiftId, Pageable pageable) {
        Page<TeacherResponseDTO> professores = service.listTeachersByShift(shiftId, pageable);
        return ResponseEntity.ok(professores);
    }

    @Tag( name = "Shift", description = "Recurso para gerenciamento de turnos" )
    @Operation(summary = "Busca todos os cursos de um turno", description = "Busca todos os cursos de um turno e retorna com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Cursos encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Turno 1\",  \"createdAt\": \"2023-01-01\", \"teacher\": 1 \"course\": 1}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar cursos")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/courses/{id}")
    public ResponseEntity<Page<CourseResponseDTO>> listarCursosPeloTurno(
            @PathVariable Long shiftId, Pageable pageable) {
        Page<CourseResponseDTO> cursos = service.listCourseByShift(shiftId, pageable);
        return ResponseEntity.ok(cursos);
    }

    @Tag( name = "Shift", description = "Recurso para gerenciamento de turnos" )
    @Operation(summary = "Busca um turno pelo ID", description = "Busca um turno pelo ID e retorna com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Turno encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = ShiftResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Turno 1\",  \"createdAt\": \"2023-01-01\", \"teacher\": 1 \"course\": 1}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar turno")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/{id}")
        public ResponseEntity<ShiftResponseDTO> getShiftById(@PathVariable Long id) {
            try {
                return new ResponseEntity<>(service.searchShift(id), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

    @Tag( name = "Shift", description = "Recurso para gerenciamento de turnos" )
    @Operation(summary = "Associa um professor a um turno", description = "Associa um professor a um turno e retorna com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Professor associado com sucesso",
            content = @Content(schema = @Schema(implementation = ShiftResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Turno 1\",  \"createdAt\": \"2023-01-01\", \"teacher\": 1 \"course\": 1}")))
    @ApiResponse(responseCode = "400", description = "Erro ao associar professor")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PostMapping("/teachers/{shiftId}/{teacherId}")
    public ResponseEntity<String> addTeacherToShift(@PathVariable Long shiftId, @PathVariable Long teacherId, @RequestParam User actor) {
        try {
            service.addTeacherToShift(shiftId, teacherId, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Tag( name = "Shift", description = "Recurso para gerenciamento de turnos" )
    @Operation(summary = "Remove um professor de um turno", description = "Remove um professor de um turno e retorna com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Professor removido com sucesso",
            content = @Content(schema = @Schema(implementation = ShiftResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Turno 1\",  \"createdAt\": \"2023-01-01\", \"teacher\": 1 \"course\": 1}")))
    @ApiResponse(responseCode = "400", description = "Erro ao remover professor")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @DeleteMapping("/teachers/{shiftId}/{teacherId}")
    public ResponseEntity<String> removeTeacherOfShift(@PathVariable Long shiftId, @PathVariable Long teacherId, @RequestParam User actor) {
        try {
            service.removeTeacherOfShift(shiftId, teacherId, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

    @Tag( name = "Shift", description = "Recurso para gerenciamento de turnos" )
    @Operation(summary = "Associa um curso a um turno", description = "Associa um curso a um turno e retorna com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Curso associado com sucesso",
            content = @Content(schema = @Schema(implementation = ShiftResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Turno 1\",  \"createdAt\": \"2023-01-01\", \"teacher\": 1 \"course\": 1}")))
    @ApiResponse(responseCode = "400", description = "Erro ao associar curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PostMapping("/teachers/{shiftId}/{courseId}")
    public ResponseEntity<String> addCourseToShift(@PathVariable Long shiftId, @PathVariable Long courseId, @RequestParam User actor) {
        try {
            service.addCourseToShift(shiftId, courseId, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Tag( name = "Shift", description = "Recurso para gerenciamento de turnos" )
    @Operation(summary = "Remove um curso de um turno", description = "Remove um curso de um turno e retorna com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Curso removido com sucesso",
            content = @Content(schema = @Schema(implementation = ShiftResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Turno 1\",  \"createdAt\": \"2023-01-01\", \"teacher\": 1 \"course\": 1}")))
    @ApiResponse(responseCode = "400", description = "Erro ao remover curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @DeleteMapping("/teachers/{shiftId}/{courseId}")
    public ResponseEntity<String> removeCourseOfShift(@PathVariable Long shiftId, @PathVariable Long courseId, @RequestParam User actor) {
        try {
            service.removeCourseOfShift(shiftId, courseId, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Tag( name = "Shift", description = "Recurso para gerenciamento de turnos" )
    @Operation(summary = "Deleta um turno", description = "Deleta um turno e retorna com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Turno deletado com sucesso",
            content = @Content(schema = @Schema(implementation = ShiftResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Turno 1\",  \"createdAt\": \"2023-01-01\", \"teacher\": 1 \"course\": 1}")))
    @ApiResponse(responseCode = "400", description = "Erro ao deletar turno")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
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


