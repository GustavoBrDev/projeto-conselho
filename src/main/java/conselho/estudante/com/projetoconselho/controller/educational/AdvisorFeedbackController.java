package conselho.estudante.com.projetoconselho.controller.educational;

import conselho.estudante.com.projetoconselho.models.dto.request.EDUCATIONAL.AdvisorFeedbackRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.educational.AdvisorFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.services.educational.AdvisorFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/advisor-feedbacks")
@RequiredArgsConstructor
public class AdvisorFeedbackController {

    private final AdvisorFeedbackService service;

    @Operation(summary = "Cria um feedback")
    @ApiResponse(responseCode = "201", description = "Feedback criado com sucesso"
            , content = @Content(schema = @Schema(implementation = AdvisorFeedbackResponseDTO.class),
            examples = @ExampleObject("{\"id\": 1, \"council\": {\"id\": 1}, \"advisor\": {\"id\": 1}, \"strengths\": \"\", \"weaknesses\": \"\", \"suggestions\": \"\", \"isApproved\": false}")))
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PostMapping
    public ResponseEntity<AdvisorFeedbackResponseDTO> create(@RequestBody @Parameter(description = "Dados do feedback") AdvisorFeedbackRequestDTO requestDTO,
                                                             @RequestParam @Parameter(description = "Usuário logado") User actor) {
        return ResponseEntity.ok(service.create(requestDTO, actor));
    }

    @Operation(summary = "Edita um feedback")
    @ApiResponse(responseCode = "200", description = "Feedback editado com sucesso"
            , content = @Content(schema = @Schema(implementation = AdvisorFeedbackResponseDTO.class),
            examples = @ExampleObject("{\"id\": 1, \"council\": {\"id\": 1}, \"advisor\": {\"id\": 1}, \"strengths\": \"\", \"weaknesses\": \"\", \"suggestions\": \"\", \"isApproved\": false}")))
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{id}")
    public ResponseEntity<AdvisorFeedbackResponseDTO> update(@PathVariable @Parameter(description = "ID do feedback") Long id,
                                                             @RequestBody @Parameter(description = "Dados do feedback") AdvisorFeedbackRequestDTO requestDTO,
                                                             @RequestParam @Parameter(description = "Usuário logado") User actor) {
        return ResponseEntity.ok(service.update(id, requestDTO, actor));
    }

    @Operation(summary = "Edita os textos de um feedback")
    @ApiResponse(responseCode = "200", description = "Textos editados com sucesso"
            , content = @Content(schema = @Schema(implementation = AdvisorFeedbackResponseDTO.class),
            examples = @ExampleObject("{\"id\": 1, \"council\": {\"id\": 1}, \"advisor\": {\"id\": 1}, \"strengths\": \"\", \"weaknesses\": \"\", \"suggestions\": \"\", \"isApproved\": false}")))
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{id}/edit-texts")
    public ResponseEntity<AdvisorFeedbackResponseDTO> editTexts(@PathVariable Long id,
                                                                @RequestParam @Parameter(description = "Forças" ) String strengths,
                                                                @RequestParam @Parameter(description = "Fracos" ) String weaknesses,
                                                                @RequestParam @Parameter(description = "Sugestões" ) String suggestions,
                                                                @RequestParam @Parameter(description = "Usuário logado") User actor) {
        return ResponseEntity.ok(service.editTexts(id, strengths, weaknesses, suggestions, actor));
    }

    @Operation(summary = "Busca todos os feedbacks")
    @ApiResponse(responseCode = "200", description = "Feedbacks encontrados com sucesso"
            , content = @Content(schema = @Schema(implementation = AdvisorFeedbackResponseDTO.class),
            examples = @ExampleObject("{\"id\": 1, \"council\": {\"id\": 1}, \"advisor\": {\"id\": 1}, \"strengths\": \"\", \"weaknesses\": \"\", \"suggestions\": \"\", \"isApproved\": false}")))
    @ApiResponse(responseCode = "400", description = "Erro encontrar feedbacks")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping
    public ResponseEntity<Page<AdvisorFeedbackResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Operation(summary = "Busca todos os feedbacks de um conselho")
    @ApiResponse(responseCode = "200", description = "Feedbacks encontrados com sucesso"
            , content = @Content(schema = @Schema(implementation = AdvisorFeedbackResponseDTO.class),
            examples = @ExampleObject("{\"id\": 1, \"council\": {\"id\": 1}, \"advisor\": {\"id\": 1}, \"strengths\": \"\", \"weaknesses\": \"\", \"suggestions\": \"\", \"isApproved\": false}")))
    @ApiResponse(responseCode = "400", description = "Erro encontrar feedbacks")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/council/{councilId}")
    public ResponseEntity<Page<AdvisorFeedbackResponseDTO>> findByCouncil(@PathVariable Long councilId, Pageable pageable) {
        return ResponseEntity.ok(service.findByCouncil(councilId, pageable));
    }

    @Operation(summary = "Busca um feedback")
    @ApiResponse(responseCode = "200", description = "Feedback encontrado com sucesso"
            , content = @Content(schema = @Schema(implementation = AdvisorFeedbackResponseDTO.class),
            examples = @ExampleObject("{\"id\": 1, \"council\": {\"id\": 1}, \"advisor\": {\"id\": 1}, \"strengths\": \"\", \"weaknesses\": \"\", \"suggestions\": \"\", \"isApproved\": false}")))
    @ApiResponse(responseCode = "400", description = "Erro encontrar feedback")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    public ResponseEntity<AdvisorFeedbackResponseDTO> findById(@PathVariable @Parameter(description = "ID do feedback") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Deleta um feedback")
    @ApiResponse(responseCode = "204", description = "Feedback deletado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao deletar feedback")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "ID do feedback") Long id,
                                       @RequestParam @Parameter(description = "Usuário logado") User actor) {
        service.delete(id, actor);
        return ResponseEntity.noContent().build();
    }
}
