package conselho.estudante.com.projetoconselho.Controller.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ADMINISTRATION.ShiftResponseDTO;
import conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION.AvaliableTeacherService;
import conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION.SHIFT.ShiftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Classe de controller da entidade Shift
 *
 * @author joana voigt
 * @since 31/03/2025
 *
 * @see AvaliableTeacherService
 *
 */
@RestController
@RequestMapping("/administration/AvaliableTeacher")
@AllArgsConstructor
@Tag( name = "Shift", description = "Recurso para gerenciamento de avaliação de professores" )
public class AvaliableTeacherController {
    private AvaliableTeacherService service;

    @Operation(summary = "Cria uma avaliação de professor", description = "Cria uma avaliação de professor e retorna o turno criado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Avaliação criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao criar avaliação")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PostMapping
    public ResponseEntity<Boolean> postAvaliableTeacher(
            @Parameter(description = "ID do professor", required = true, example = "1") @RequestParam @Valid Long teacherId,
            @Parameter(description = "ID das materias", required = true) @RequestParam List<Long> subjectIds) {

        try {
            return new ResponseEntity<>(service.create(teacherId, subjectIds), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Edita uma avaliação de professor", description = "Edita uma avaliação de professor e retorna o turno criado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Avaliação editada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao editar avaliação")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateAvaliableTeacher(
        @Parameter (description = "ID da avaliação do professor", required = true, example = "1") @RequestParam @Valid Long avaliableTeacherId,
        @Parameter(description = "ID do professor", required = true, example = "1") @RequestParam @Valid Long teacherId,
        @Parameter(description = "ID das materias", required = true) @RequestParam List<Long> subjectIds){

        try {
            return new ResponseEntity<>(service.update(avaliableTeacherId, teacherId, subjectIds), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Busca uma lista de avaliações de professor", description = "Busca uma lista de avaliações de professor e retorna o turno criado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Avaliação encontrada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao buscar avaliação")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/allAvaliableTeachers")
    public ResponseEntity<List> getAllAvaliableTeachers() {

        try {
            return new ResponseEntity<>(service.getAllAvaliableTeachers(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Deleta uma avaliação de professor", description = "Celeta uma avaliação de professor e retorna o turno criado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Avaliação deletada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao deletar avaliação")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvaliableTeacher(
            @Parameter(description = "ID da avaliação do professor", required = true, example = "1") @PathVariable Long id) {

        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 

}
