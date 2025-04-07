package conselho.estudante.com.projetoconselho.controller.educational;

import conselho.estudante.com.projetoconselho.models.dto.request.EDUCATIONAL.PersonalFeedbackRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.request.EDUCATIONAL.TeacherPreCouncilRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.EDUCATIONAL.TeacherPreCouncilResponseDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.USERS.StudentResponseDTO;
import conselho.estudante.com.projetoconselho.services.educational.teacher_pre_council.TeacherPreCouncilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

import java.util.Date;

/**
 * Classe de controller da entidade TeacherPreCouncil
 *
 * @author joana voigt
 * @since 31/03/2025
 *
 * @see TeacherPreCouncilService
 *
 */
@RestController
@RequestMapping("/educational/teacher-pre-councils")
@AllArgsConstructor
@Tag(name = "TeacherPreCouncils", description = "Recurso para o gerenciamento de TeacherPreCouncils")
public class TeacherPreCouncilController {
    private TeacherPreCouncilService service;

    @Operation(summary = "Cria um pré conselho de professor", description = "Cria um pré conselho de professor e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pré conselho criado com sucesso",
            content = @Content(schema = @Schema(implementation = TeacherPreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"council\": {Objeto de council}, \"classe\": {Objeto de classe}, \"isFilled\": true, \"teacher\": {Objeto de teacher}, \"subject\": {Objeto de subject}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao criar pré conselho")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PostMapping
    public ResponseEntity<TeacherPreCouncilResponseDTO> postTeacherPreCouncil(
            @Parameter(description = "Pré conselho do professor a ser criado", content =
            @Content(schema = @Schema(implementation = TeacherPreCouncilResponseDTO.class)),
                    required = true, example = "{" +
                    "\"id\": 1, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"council\": {Objeto de council}, \"classe\": {Objeto de classe}, \"isFilled\": true, \"teacher\": {Objeto de teacher}, \"subject\": {Objeto de subject}, \"feedbacks\": []}")
            @RequestBody @Valid TeacherPreCouncilRequestDTO teacherPreCouncilRequestDTO) {

        try {
            return new ResponseEntity<>(service.create(teacherPreCouncilRequestDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca um pré conselho de professor pelo ID", description = "Busca um pré conselho de professor pelo ID e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pré conselho encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = TeacherPreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"council\": {Objeto de council}, \"classe\": {Objeto de classe}, \"isFilled\": true, \"teacher\": {Objeto de teacher}, \"subject\": {Objeto de subject}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao encontrar pré conselho")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/{id}")
    public ResponseEntity<TeacherPreCouncilResponseDTO> getTeacherPreCouncilById(
            @Parameter(description = "ID do pré conselho", required = true, example = "1")
            @PathVariable Long id) {

        try {
            return new ResponseEntity<>(service.search(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita um pré conselho de professor", description = "Edita um pré conselho de professor e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pré conselho editado com sucesso",
            content = @Content(schema = @Schema(implementation = TeacherPreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"council\": {Objeto de council}, \"classe\": {Objeto de classe}, \"isFilled\": true, \"teacher\": {Objeto de teacher}, \"subject\": {Objeto de subject}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar pré conselho")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PutMapping("/{id}")
    public ResponseEntity<TeacherPreCouncilResponseDTO> putTeacherPreCouncil(
            @Parameter(description = "Dados do pré conselho a ser editado", content =
            @Content(schema = @Schema(implementation = TeacherPreCouncilRequestDTO.class)),
                    required = true, example = "{" +
                    "\"id\": 1, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"council\": {Objeto de council}, \"classe\": {Objeto de classe}, \"isFilled\": true, \"teacher\": {Objeto de teacher}, \"subject\": {Objeto de subject}, \"feedbacks\": []}")
            @RequestBody @Valid TeacherPreCouncilRequestDTO teacherPreCouncilRequestDTO,
            @Parameter(description = "ID do pré conselho", required = true, example = "1")
            @PathVariable Long id) {

        try {
            return new ResponseEntity<>(service.update(teacherPreCouncilRequestDTO, id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Deleta um pré conselho de professor", description = "Deleta um pré conselho de professor e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pré conselho deletado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao deletar pré conselho")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacherPreCouncil(
            @Parameter(description = "ID do pré conselho", required = true, example = "1") @PathVariable Long id) {

        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita a data de inicio de um pré conselho de professor", description = "Edita a data de inicio de um pré conselho de professor e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pré conselho editado com sucesso",
            content = @Content(schema = @Schema(implementation = TeacherPreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"council\": {Objeto de council}, \"classe\": {Objeto de classe}, \"isFilled\": true, \"teacher\": {Objeto de teacher}, \"subject\": {Objeto de subject}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar pré conselho")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editStartDate/{id}")
    public ResponseEntity<String> editStartDate(
            @Parameter(description = "ID do pré conselho a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Nova data de inicio do pré conselho", required = true) Date startDate) {

        try {
            service.editStartDate(id, startDate);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita a data de término de um pré conselho de professor", description = "Edita a data de término de um pré conselho de professor e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pré conselho editado com sucesso",
            content = @Content(schema = @Schema(implementation = TeacherPreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"council\": {Objeto de council}, \"classe\": {Objeto de classe}, \"isFilled\": true, \"teacher\": {Objeto de teacher}, \"subject\": {Objeto de subject}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar pré conselho")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editEndDate/{id}")
    public ResponseEntity<String> editEndDate(
            @Parameter(description = "ID do pré conselho a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Nova data de término do pré conselho", required = true) Date endDate) {

        try {
            service.editEndDate(id, endDate);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita o estado de preenchimento de um pré conselho de professor", description = "Edita o estado de preenchimento de um pré conselho de professor e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pré conselho editado com sucesso",
            content = @Content(schema = @Schema(implementation = TeacherPreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"council\": {Objeto de council}, \"classe\": {Objeto de classe}, \"isFilled\": true, \"teacher\": {Objeto de teacher}, \"subject\": {Objeto de subject}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar pré conselho")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editIsFilled/{id}")
    public ResponseEntity<String> editIsFilled(
            @Parameter(description = "ID do pré conselho a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Novo estado de preenchimento do pré conselho", required = true) Boolean isFilled) {

        try {
            service.editIsFilled(id, isFilled);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita a matéria associada a um pré conselho de professor", description = "Edita a matéria associada a um pré conselho de professor e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Materia editada com sucesso",
            content = @Content(schema = @Schema(implementation = TeacherPreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"council\": {Objeto de council}, \"classe\": {Objeto de classe}, \"isFilled\": true, \"teacher\": {Objeto de teacher}, \"subject\": {Objeto de subject}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar materia")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editSubject/{id}")
    public ResponseEntity<String> editSubject(
            @Parameter(description = "ID do pré conselho a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Novo materia associada do pré conselho", required = true) Long subjectId) {

        try {
            service.editSubject(id, subjectId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(summary = "Busca todos os pré conselhos de professor", description = "Busca todos os pré conselhos de professor e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pré conselhos encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = TeacherPreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"council\": {Objeto de council}, \"classe\": {Objeto de classe}, \"isFilled\": true, \"teacher\": {Objeto de teacher}, \"subject\": {Objeto de subject}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro encontrar pré conselhos")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping()
    public ResponseEntity<Page<TeacherPreCouncilResponseDTO>> getAllPreCouncils(
            @Parameter(description = "Busca todos os pré conselhos", content =
            @Content(schema = @Schema(implementation = TeacherPreCouncilResponseDTO.class)),
                    required = true, example = "{" +
                    "\"id\": 1, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"council\": {Objeto de council}, \"classe\": {Objeto de classe}, \"isFilled\": true, \"teacher\": {Objeto de teacher}, \"subject\": {Objeto de subject}, \"feedbacks\": []}")
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        try {
            return new ResponseEntity<>(service.listAllTeacherPreCouncils(pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca todos os alunos", description = "Busca todos os alunos e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Alunos encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = TeacherPreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"council\": {Objeto de council}, \"classe\": {Objeto de classe}, \"isFilled\": true, \"teacher\": {Objeto de teacher}, \"subject\": {Objeto de subject}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro encontrar alunos")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/students")
    public ResponseEntity<Page<StudentResponseDTO>> getStudents(
            @Parameter(description = "ID do pré conselho", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "Pagina para listar alunos", required = true)
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<StudentResponseDTO> students = service.listAllStudents(pageable);
        return ResponseEntity.ok(students);
    }

    @Operation(summary = "Adiciona um feedback ao pré conselho", description = "Adiciona um feedback ao pré conselho e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Feedback adicionado com sucesso",
            content = @Content(schema = @Schema(implementation = TeacherPreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"council\": {Objeto de council}, \"classe\": {Objeto de classe}, \"isFilled\": true, \"teacher\": {Objeto de teacher}, \"subject\": {Objeto de subject}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar feedback")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/addFeedbacks/{id}/{feedback}")
    public ResponseEntity<String> addFeedbackToPreCouncil(
            @Parameter(description = "ID do pré conselho", required = true) @PathVariable Long id,
            @Parameter(description = "Feedback", required = true) @PathVariable PersonalFeedbackRequestDTO feedback) {

        try {
            service.addFeedback(id, feedback);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Deleta um feedback do pré conselho", description = "Deleta um feedback do pré conselho e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Feedback deletado com sucesso",
            content = @Content(schema = @Schema(implementation = TeacherPreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"council\": {Objeto de council}, \"classe\": {Objeto de classe}, \"isFilled\": true, \"teacher\": {Objeto de teacher}, \"subject\": {Objeto de subject}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao deletar feedback")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/removeFeedback/{id}/{feedback}")
    public ResponseEntity<String> deleteFeedbackToPreCouncil(
            @Parameter(description = "ID do pré conselho", required = true) @PathVariable Long id,
            @Parameter(description = "Feedback", required = true) @PathVariable PersonalFeedbackRequestDTO feedback) {

        try {
            service.removeFeedback(id, feedback);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
