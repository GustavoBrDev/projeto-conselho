package conselho.estudante.com.projetoconselho.controller.educational;

import conselho.estudante.com.projetoconselho.models.dto.request.EDUCATIONAL.RepresentativePreCouncilRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.educational.RepresentativePreCouncilResponseDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.users.TeacherResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.users.Teacher;
import conselho.estudante.com.projetoconselho.services.educational.RepresentativePreCouncilService;
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
 * Classe de controller da entidade RepresentativePreCouncil
 *
 * @author joana voigt
 * @since 31/03/2025
 *
 * @see RepresentativePreCouncilService
 *
 */
@RestController
@RequestMapping("/educational/representative-pre-councils")
@AllArgsConstructor
@Tag(name = "RepresentativePreCouncil", description = "Recurso para o gerenciamento de pré conselhos de representantes")
public class RepresentativePreCouncilController {
    private RepresentativePreCouncilService service;

    @Operation(summary = "Cria um pré conselho de representante", description = "Cria um pré conselho de representante e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pré conselho criado com sucesso",
            content = @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")))
    @ApiResponse(responseCode = "400", description = "Erro ao criar pré conselho")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PostMapping
    public ResponseEntity<RepresentativePreCouncilResponseDTO> postRepresentativePreCouncil(
            @Parameter(description = "Pré conselho do representante a ser criado", content =
            @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class)),
                    required = true, example = "{" +
                    "\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")
            @RequestBody @Valid RepresentativePreCouncilRequestDTO representativePreCouncilRequestDTO) {

        try {
            return new ResponseEntity<>(service.create(representativePreCouncilRequestDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita um pré conselho de representante", description = "Edita um pré conselho de representante e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pré conselho editado com sucesso",
            content = @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar pré conselho")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PutMapping("/{id}")
    public ResponseEntity<RepresentativePreCouncilResponseDTO> putRepresentativePreCouncil(
            @Parameter(description = "Dados do pré conselho a ser editado", content =
            @Content(schema = @Schema(implementation = RepresentativePreCouncilRequestDTO.class)),
                    required = true, example = "{" +
                    "\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")
            @RequestBody @Valid RepresentativePreCouncilRequestDTO representativePreCouncilRequestDTO,
            @Parameter(description = "ID do pré conselho", required = true, example = "1")
            @PathVariable Long id) {

        try {
            return new ResponseEntity<>(service.update(id, representativePreCouncilRequestDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita a data de inicio de um pré conselho de representante", description = "Edita a data de inicio de um pré conselho de representante e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pré conselho editado com sucesso",
            content = @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")))
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

    @Operation(summary = "Edita a data de término de um pré conselho de representante", description = "Edita a data de término de um pré conselho de representante e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pré conselho editado com sucesso",
            content = @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")))
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

    @Operation(summary = "Edita o estado de preenchimento de um pré conselho de representante", description = "Edita o estado de preenchimento de um pré conselho de representante e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pré conselho editado com sucesso",
            content = @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")))
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

    @Operation(summary = "Edita o feedback de um supervisor", description = "Edita o feedback de um supervisor e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Feedback editado com sucesso",
            content = @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar feedback")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editSupervisorFeedback/{id}")
    public ResponseEntity<String> editSupervisorFeedback(
            @Parameter(description = "ID do pré conselho a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Novo feedback do supervisor", required = true) Long supervisorFeedbackId) {

        try {
            service.editSupervisorFeedback(id, supervisorFeedbackId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita o feedback de um conselheiro", description = "Edita o feedback de um conselheiro e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Feedback editado com sucesso",
            content = @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar feedback")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editAdvisorFeedback/{id}")
    public ResponseEntity<String> editAdvisorFeedback(
            @Parameter(description = "ID do pré conselho a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Novo feedback do conselheiro", required = true) Long advisorFeedbackId) {

        try {
            service.editAdvisorFeedback(id, advisorFeedbackId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca todos os pré conselhos de representante", description = "Busca todos os pré conselhos de representante e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pré conselhos encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")))
    @ApiResponse(responseCode = "400", description = "Erro encontrar pré conselhos")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping()
    public ResponseEntity<Page<RepresentativePreCouncilResponseDTO>> getAllPreCouncils(
            @Parameter(description = "Busca todos os pré conselhos", content =
            @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class)),
                    required = true, example = "{" +
                    "\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        try {
            return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca todos os professores", description = "Busca todos os professores  e retorna  com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Professores encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")))
    @ApiResponse(responseCode = "400", description = "Erro ao encontrar professores")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/teachers")
    public ResponseEntity<Page<TeacherResponseDTO>> getTeachers(
            @Parameter (description = "ID do pré conselho", required = true, example = "1") @PathVariable Long id,
            @Parameter (description = "Pagina para listar professores", required = true)
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        Page<Teacher> teachers = service.findAllTeachers(pageable);
        Page<TeacherResponseDTO> teacherResponseDTOs = teachers.map(Teacher::toDTO);
        return ResponseEntity.ok(teacherResponseDTOs);
    }

    @Operation(summary = "Busca um pré conselho de representante pelo ID", description = "Busca um pré conselho de representante pelo ID e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pré conselho encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")))
    @ApiResponse(responseCode = "400", description = "Erro ao encontrar pré conselho")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/{id}")
    public ResponseEntity<RepresentativePreCouncilResponseDTO> getRepresentativePreCouncilById(
            @Parameter(description = "ID do pré conselho", required = true, example = "1")
            @PathVariable Long id) {

        try {
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca um pré conselho de representante pela classe", description = "Busca um pré conselho de representante pela classe e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pré conselho encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")))
    @ApiResponse(responseCode = "400", description = "Erro ao encontrar pré conselho")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/FindByClasse/{idClasse}")
    public ResponseEntity<Page<RepresentativePreCouncilResponseDTO>> findByClasse(
            @Parameter(description = "ID da classe", required = true, example = "1")
            @PathVariable Long idClasse,
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        try {
            return new ResponseEntity<>(service.findByClasse(idClasse, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca um pré conselho de representante pelo termo de pesquisa", description = "Busca um pré conselho de representante pelo termo de pesquisa e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pré conselho encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")))
    @ApiResponse(responseCode = "400", description = "Erro ao encontrar pré conselho")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/findByTerm/{term}")
    public ResponseEntity<Page<RepresentativePreCouncilResponseDTO>> FindByTerm(
            @Parameter(description = "Termo de pesquisa", required = true) @PathVariable String term,
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        try {
            return new ResponseEntity<>(service.search(term, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca um pré conselho de representante por um intervalo de datas", description = "Busca um pré conselho de representante por um intervalo de datas e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pré conselho encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")))
    @ApiResponse(responseCode = "400", description = "Erro ao encontrar pré conselho")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/findByDateRange")
    public ResponseEntity<Page<RepresentativePreCouncilResponseDTO>> findByDateRange(
            @Parameter(description = "data de inicio", required = true, example = "20-02-2005") @PathVariable Date startDate,
            @Parameter(description = "data de termino", required = true, example = "20-04-2005") @PathVariable Date endDate,
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        try {
            return new ResponseEntity<>(service.findByDateRange(startDate, endDate, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca um pré conselho de representante pelo status de preenchimento", description = "Busca um pré conselho de representante pelo status de preenchimento e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pré conselho encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")))
    @ApiResponse(responseCode = "400", description = "Erro ao encontrar pré conselho")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/findByFillStatus/{isFilled}")
    public ResponseEntity<Page<RepresentativePreCouncilResponseDTO>> findByFillStatus(
            @Parameter(description = "Termo de pesquisa", required = true) @PathVariable Boolean isFilled,
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        try {
            return new ResponseEntity<>(service.findByFillStatus(isFilled, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Adiciona um feedback de professor ao pré-conselho", description = "Adiciona um feedback de professor ao pré-conselho e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Feedback adicionado com sucesso",
            content = @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar feedback")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/addTeacherFeedback/{id}/{teacherFeedbackId}")
    public ResponseEntity<String> addTeacherFeedback(
            @Parameter(description = "ID do pré conselho", required = true) @PathVariable Long id,
            @Parameter(description = "ID do feedback de professor", required = true) @PathVariable Long teacherFeedbackId) {

        try {
            service.addTeacherFeedback(id, teacherFeedbackId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Adiciona um feedback de conselheiro ao pré-conselho", description = "Adiciona um feedback de conselheiro ao pré-conselho e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Feedback adicionado com sucesso",
            content = @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar feedback")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/addAdvisorFeedback/{id}/{advisorFeedbackId}")
    public ResponseEntity<String> addAdvisorFeedback(
            @Parameter(description = "ID do pré conselho", required = true) @PathVariable Long id,
            @Parameter(description = "ID do feedback de conselheiro", required = true) @PathVariable Long advisorFeedbackId) {

        try {
            service.addAdvisorFeedback(id, advisorFeedbackId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Adiciona um feedback de supervisor ao pré-conselho", description = "Adiciona um feedback de supervisor ao pré-conselho e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Feedback adicionado com sucesso",
            content = @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar feedback")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/addSupervisorFeedback/{id}/{supervisorFeedbackId}")
    public ResponseEntity<String> addSupervisorFeedback(
            @Parameter(description = "ID do pré conselho", required = true) @PathVariable Long id,
            @Parameter(description = "ID do feedback de supervisor", required = true) @PathVariable Long supervisorFeedbackId) {

        try {
            service.addSupervisorFeedback(id, supervisorFeedbackId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Adiciona um feedback de item ao pré-conselho", description = "Adiciona um feedback de item ao pré-conselho e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Feedback adicionado com sucesso",
            content = @Content(schema = @Schema(implementation = RepresentativePreCouncilResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"Council\": {Objeto de council}, \"createdAt\": \"2023-01-01\", \"startDate\": \"2023-02-01\", \"endDate\": \"2025-02-01\", \"classe\": {Objeto de classe}, \"isFilled\": true, \"teachers\": [Lista de teachers], \"AdvisorFeedback\": {Objeto de AdvisorFeedback}, \"SupervisorFeedback\": {Objeto de SupervisorFeedback}, \"teacherFeebacks\": [Lista de teacherFeebacks], \"itemFeedbacks\": [Lista de itemFeedbacks]}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar feedback")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/addItemFeedback/{id}/{itemFeedbackId}")
    public ResponseEntity<String> addItemFeedback(
            @Parameter(description = "ID do pré conselho", required = true) @PathVariable Long id,
            @Parameter(description = "ID do feedback de item", required = true) @PathVariable Long itemFeedbackId) {

        try {
            service.addItemFeedback(id, itemFeedbackId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Deleta um pré conselho de representante", description = "Deleta um pré conselho de representante e retorna com um status 200")
    @ApiResponse(responseCode = "200", description = "Pre conselho deletado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao deletar pré conselho")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepresentativePreCouncil(
            @Parameter(description = "ID do pré conselho", required = true, example = "1") @PathVariable Long id) {

        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
