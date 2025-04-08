package conselho.estudante.com.projetoconselho.controller.educational;

import conselho.estudante.com.projetoconselho.models.dto.request.EDUCATIONAL.*;
import conselho.estudante.com.projetoconselho.models.dto.response.educational.CouncilResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.educational.*;
import conselho.estudante.com.projetoconselho.models.entity.users.Student;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.services.educational.council.CouncilService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Classe de controller da entidade Council
 * @author Gustavo Stinghen
 * @since 07/04/2025
 * @see CouncilService
 */

@RestController
@RequestMapping("/council")
@AllArgsConstructor
@Tag(name = "Council", description = "Endpoints para gerenciamento de councils")
public class CouncilController {

    private CouncilService service;

    @Operation(summary = "Cria um council", description = "Cria um council e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Council criado com sucesso",
        content = @Content(schema = @Schema(implementation = CouncilResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao criar council")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PostMapping
    public ResponseEntity<CouncilResponseDTO> create(
            @RequestBody @Valid @Parameter(description = "Dados do council a ser criado", content = @Content(schema = @Schema(implementation = CouncilRequestDTO.class), examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")), required = true) CouncilRequestDTO requestDTO,
            @RequestParam @Parameter(description = "Usuário que está criando o council") User actor) {
        return ResponseEntity.ok(service.create(requestDTO, actor));
    }

    @Operation(summary = "Edita um council", description = "Edita um council e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Council editado com sucesso",
        content = @Content(schema = @Schema(implementation = CouncilResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar council")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PutMapping("/{id}")
    public ResponseEntity<CouncilResponseDTO> update(
        @RequestBody @Parameter (description = "Dados do council a ser editado", content = @Content(schema = @Schema(implementation = CouncilRequestDTO.class), examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")), required = true) CouncilRequestDTO requestDTO,
        @PathVariable @Parameter(description = "ID do council", required = true, example = "1") Long id,
        @RequestParam @Parameter (description = "Usuário que está editando o council") User actor) {

        return ResponseEntity.ok(service.update(id, requestDTO, actor));
    }

    @Operation(summary = "Busca um council", description = "Busca um council e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Council encontrado com sucesso",
        content = @Content(schema = @Schema(implementation = CouncilResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar council")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/{id}")
    public ResponseEntity<CouncilResponseDTO> findById(@PathVariable @Parameter(description = "ID do council", required = true, example = "1") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Busca todos os councils", description = "Busca todos os councils e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Councils encontrados com sucesso",
        content = @Content(schema = @Schema(implementation = CouncilResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar councils")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping
    public ResponseEntity<Iterable<CouncilResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Operation(summary = "Busca todos os teachers de um council", description = "Busca todos os teachers de um council e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Teachers encontrados com sucesso",
        content = @Content(schema = @Schema(implementation = AvaliableTeacher.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar teachers")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/teachers/{id}")
    public ResponseEntity<Page<AvaliableTeacher>> findTeachersByCouncil( @PathVariable @Parameter(description = "ID do council", required = true, example = "1") Long id, Pageable pageable) {
        return ResponseEntity.ok(service.findTeachersByCouncil(id, pageable));
    }

    @Operation(summary = "Busca todos os students de um council", description = "Busca todos os students de um council e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Students encontrados com sucesso",
        content = @Content(schema = @Schema(implementation = Student.class),
                examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar students")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/students/{id}")
    public Page<Student> findViewedStudents( @PathVariable @Parameter(description = "ID do council", required = true, example = "1") Long id, Pageable pageable) {
        return service.findViewedStudents(id, pageable);
    }

    @Operation(summary = "Busca todos os students de um council", description = "Busca todos os students de um council e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Students encontrados com sucesso",
        content = @Content(schema = @Schema(implementation = Student.class),
                examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Gustavo Stinghen\", \"registration\": 123456, \"email\": \"7G9Gt@example.com\", \"password\": \"123456\", \"image\": \"https://example.com/image.jpg\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao buscar students")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/studentsWhoNeedToBeCalled/{id}")
    public Page<Student> findStudentsWhoNeedToBeCalled( @PathVariable @Parameter(description = "ID do council", required = true, example = "1") Long id, Pageable pageable) {
        return service.findStudentsWhoNeedToBeCalled(id, pageable);
    }

    @Operation(summary = "Edita a data de conclusão de um council", description = "Edita a data de conclusão de um council e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Council editado com sucesso",
        content = @Content(schema = @Schema(implementation = CouncilResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar council")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editDate/{id}")
    public ResponseEntity<CouncilResponseDTO> editDate(@PathVariable @Parameter(description = "ID do council", required = true, example = "1") Long id, @RequestParam @Parameter(description = "Nova data de conclusão do council") Date endDate, @RequestParam @Parameter(description = "Usuário que está editando o council") User actor) {
        return ResponseEntity.ok(service.editDate(id, endDate, actor));
    }

    @Operation(summary = "Edita a data de conclusão do pré conselho dos professores", description = "Edita a data de conclusão do pré conselho dos professores e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Council editado com sucesso",
        content = @Content(schema = @Schema(implementation = CouncilResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar council")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editTeacherPreCouncilEndDate/{id}")
    public ResponseEntity<CouncilResponseDTO> editTeacherPreCouncilEndDate (
            @PathVariable @Parameter(description = "ID do council", required = true, example = "1") Long id,
            @RequestParam @Parameter(description = "Nova data de conclusão do pré conselho dos professores") Date endDate,
            @RequestParam @Parameter(description = "Usuário que está editando o council") User actor
    ){
        return ResponseEntity.ok(service.editTeacherPreCouncilEndDate(id, endDate, actor));
    }

    @Operation(summary = "Edita a data de conclusão do pré conselho dos representantes", description = "Edita a data de conclusão do pré conselho dos representantes e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Council editado com sucesso",
        content = @Content(schema = @Schema(implementation = CouncilResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar council")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editRepresentativePreCouncilEndDate/{id}")
    public ResponseEntity<CouncilResponseDTO> editRepresentativePreCouncilEndDate (
            @PathVariable @Parameter(description = "ID do council", required = true, example = "1") Long id,
            @RequestParam @Parameter(description = "Nova data de conclusão do pré conselho dos representantes") Date endDate,
            @RequestParam @Parameter(description = "Usuário que está editando o council") User actor
    ){
        return ResponseEntity.ok(service.editRepresentativePreCouncilEndDate(id, endDate, actor));
    }

    @Operation(summary = "Inicia o pré conselho dos professores", description = "Inicia o pré conselho dos professores e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Council editado com sucesso",
        content = @Content(schema = @Schema(implementation = CouncilResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar council")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/startTeacherPreCouncil/{id}")
    public ResponseEntity<CouncilResponseDTO> startTeacherPreCouncil (
            @PathVariable @Parameter(description = "ID do council", required = true, example = "1") Long id,
            @RequestParam @Parameter(description = "Nova data de conclusão do pré conselho dos professores") Date endDate
    ){
        return ResponseEntity.ok(service.startTeacherPreCouncil(id, endDate));
    }

    @Operation(summary = "Inicia o pré conselho dos representantes", description = "Inicia o pré conselho dos representantes e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Council editado com sucesso",
        content = @Content(schema = @Schema(implementation = CouncilResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar council")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/startRepresentativePreCouncil/{id}")
    public ResponseEntity<CouncilResponseDTO> startRepresentativePreCouncil (
            @PathVariable @Parameter(description = "ID do council", required = true, example = "1") Long id,
            @RequestParam @Parameter(description = "Nova data de conclusão do pré conselho dos representantes") Date endDate
    ){
        return ResponseEntity.ok(service.startRepresentativePreCouncil(id, endDate));
    }

    @Operation(summary = "Finaliza o pré conselho dos professores", description = "Finaliza o pré conselho dos professores e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Council editado com sucesso",
        content = @Content(schema = @Schema(implementation = CouncilResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar council")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/endTeacherPreCouncil/{id}")
    public ResponseEntity<CouncilResponseDTO> endTeacherPreCouncil (
            @PathVariable @Parameter(description = "ID do council", required = true, example = "1") Long id
    ){
        return ResponseEntity.ok(service.endTeacherPreCouncil(id));
    }

    @Operation(summary = "Finaliza o conselho", description = "Finaliza o conselho e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Council editado com sucesso",
        content = @Content(schema = @Schema(implementation = CouncilResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar council")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/endCouncil/{id}")
    public ResponseEntity<CouncilResponseDTO> endCouncil (
            @PathVariable @Parameter(description = "ID do council", required = true, example = "1") Long id
    ){
        return ResponseEntity.ok(service.endCouncil(id));
    }

    @Operation(summary = "Entrega o feedback", description = "Entrega o feedback e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Council editado com sucesso",
        content = @Content(schema = @Schema(implementation = CouncilResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar council")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/deliverFeedback/{id}")
    public ResponseEntity<CouncilResponseDTO> deliverFeedback (
            @PathVariable @Parameter(description = "ID do council", required = true, example = "1") Long id
    ){
        return ResponseEntity.ok(service.deliverFeedback(id));
    }

    @Operation(summary = "Finaliza o pré conselho dos representantes", description = "Finaliza o pré conselho dos representantes e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Council editado com sucesso",
        content = @Content(schema = @Schema(implementation = CouncilResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar council")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/endRepresentativePreCouncil/{id}")
    public ResponseEntity<CouncilResponseDTO> endRepresentativePreCouncil (
            @PathVariable @Parameter(description = "ID do council", required = true, example = "1") Long id
    ){
        return ResponseEntity.ok(service.endRepresentativePreCouncil(id));
    }

    @Operation(summary = "Adiciona um professor ao pré conselho", description = "Adiciona um professor ao pré conselho e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Professor adicionado ao pré conselho com sucesso",
        content = @Content(schema = @Schema(implementation = CouncilResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar professor ao pré conselho")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/addTeacherPreCouncil/{id}")
    public ResponseEntity<CouncilResponseDTO> addTeacherPreCouncil (
            @PathVariable @Parameter(description = "ID do council", required = true, example = "1") Long id,
            @RequestParam @Parameter(description = "Professor do pré conselho", required = true, content = @Content(schema = @Schema(implementation = TeacherPreCouncilRequestDTO.class), examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")) ) TeacherPreCouncilRequestDTO teacherPreCouncil,
            @RequestParam @Parameter(description = "Usuário que adicionou o professor") User actor
    ) {
        return ResponseEntity.ok(service.addTeacherPreCouncil(id, teacherPreCouncil.toEntity(), actor));
    }

    @Operation(summary = "Atualiza o pré conselho dos representantes", description = "Atualiza o pré conselho dos representantes e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Representante do pré conselho atualizado com sucesso",
        content = @Content(schema = @Schema(implementation = CouncilResponseDTO.class), examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar representante do pré conselho")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/updateRepresentativePreCouncil/{id}")
    public ResponseEntity<CouncilResponseDTO> updateRepresentativePreCouncil (
            @PathVariable @Parameter(description = "ID do council", required = true, example = "1") Long id,
            @RequestParam @Parameter(description = "Representante do pré conselho", required = true, content = @Content( schema = @Schema(implementation = RepresentativePreCouncil.class), examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}"))) RepresentativePreCouncil representativePreCouncil
    ) {
        return ResponseEntity.ok(service.updateRepresentativePreCouncil(id, representativePreCouncil));
    }

    @Operation(summary = "Adiciona um feedback ao council", description = "Adiciona um feedback ao council e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Feedback adicionado ao council com sucesso",
        content = @Content(schema = @Schema(implementation = CouncilResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar feedback ao council")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/addFeedback/{id}")
    public ResponseEntity<CouncilResponseDTO> addFeedback (
            @PathVariable @Parameter(description = "ID do council", required = true, example = "1") Long id,
            @RequestParam @Parameter(description = "Feedback", required = true, content = @Content(schema = @Schema(implementation = PersonalFeedbackRequestDTO.class), examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}"))) PersonalFeedbackRequestDTO feedback,
            @RequestParam @Parameter(description = "Usuário que adicionou o feedback") User actor
    ) {
        return ResponseEntity.ok(service.addFeedback(id, feedback.convert(), actor));
    }

    @PatchMapping("updateClassFeedback/{id}")
    public ResponseEntity<CouncilResponseDTO> updateClassFeedback (
            @PathVariable @Parameter(description = "ID do council", required = true, example = "1") Long id,
            @RequestParam @Parameter(description = "Feedback", required = true, content = @Content(schema = @Schema(implementation = ClassFeedbackRequestDTO.class), examples = @ExampleObject(value = "{\"id\": 1, \"createdAt\": \"2023-01-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"feedbacks\": []}"))) ClassFeedbackRequestDTO feedback
    ) {
        return ResponseEntity.ok(service.updateClassFeedback(id, feedback.convert()));
    }

    @Operation(summary = "Deleta um council", description = "Deleta um council e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Council deletado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao deletar council")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "ID do council", required = true, example = "1") Long id, @Parameter(description = "Usuário que deletou o council", required = true) @RequestParam User actor) {
        service.delete(id, actor);
        return ResponseEntity.ok().build();
    }
}
