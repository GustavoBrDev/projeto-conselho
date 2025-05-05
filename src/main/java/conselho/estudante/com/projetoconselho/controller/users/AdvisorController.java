package conselho.estudante.com.projetoconselho.controller.users;

import conselho.estudante.com.projetoconselho.models.dto.request.users.AdvisorRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.users.AdvisorResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Notification;
import conselho.estudante.com.projetoconselho.models.entity.users.Admin;
import conselho.estudante.com.projetoconselho.services.users.AdvisorService;
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


/**
 * Classe de controller da entidade Advisor
 * @author Gustavo Stinghen
 * @since 23/04/2025
 * @see AdvisorService
 */

@RestController
@RequestMapping("/users/advisors")
@Tag(name = "Advisor", description = "Recurso para gerenciamento de orientadores")
@AllArgsConstructor
public class AdvisorController {

    private AdvisorService service;

    @Operation(summary = "Cria um orientador", description = "Cria um orientador e retorna o orientador criado com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "orientador criado com sucesso",
            content = @Content(schema = @Schema(implementation = AdvisorResponseDTO.class),
                    examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao criar orientador")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PostMapping
    public ResponseEntity<AdvisorResponseDTO> postAdvisor(
            @Parameter(description = "orientador a ser criado", content =
            @Content (schema = @Schema(implementation = AdvisorRequestDTO.class)),
                    required = true, example = "{" +
                    "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")
            @RequestBody @Valid AdvisorRequestDTO AdvisorRequestDTO/*,
            @RequestParam @Parameter(description = "Usuário que criou o orientador", required = true) User actor*/) {

        try {
            Admin actor = Admin.builder()
                    .id(1L)
                    .username("adminTrabalhandoCom@Senai")
                    .password("adminConselho@estudante.com")
                    .build();
            return new ResponseEntity<>(service.create(AdvisorRequestDTO, actor), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita um orientador", description = "Edita um orientador e retorna o orientador editado com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "orientador editado com sucesso",
            content = @Content(schema = @Schema(implementation = AdvisorResponseDTO.class),
                    examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar orientador")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PutMapping("/{id}")
    public ResponseEntity<AdvisorResponseDTO> putAdvisor(
            @Parameter(description = "orientador a ser editado", content =
            @Content(schema = @Schema(implementation = AdvisorRequestDTO.class)),
                    required = true, example = "{" +
                    "\"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")
            @RequestBody @Valid AdvisorRequestDTO AdvisorRequestDTO,
            @Parameter(description = "ID do orientador a ser editado", required = true) @PathVariable Long id/*,
            @RequestParam @Parameter(description = "Usuário que editou o orientador", required = true) User actor*/) {

        try {

            Admin actor = Admin.builder()
                    .id(1L)
                    .username("adminTrabalhandoCom@Senai")
                    .password("adminConselho@estudante.com")
                    .build();

            return new ResponseEntity<>(service.update(id, AdvisorRequestDTO, actor), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita o nome de um orientador", description = "Edita o nome de um orientador e retorna o orientador editado com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "orientador editado com sucesso",
            content = @Content(schema = @Schema(implementation = AdvisorResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Robson\", \"email\": \"roberto123@gmail\",  \"createdAt\": \"2023-01-01\", \"register\": \"12345\", \"notifications\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar orientador")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editName/{id}")
    public ResponseEntity<String> editName(
            @Parameter (description = "ID do orientador a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Novo nome do orientador", required = true) String name/*,
        @RequestParam @Parameter(description = "Usuário que editou o orientador", required = true) User actor*/){

        try {

            Admin actor = Admin.builder()
                    .id(1L)
                    .username("adminTrabalhandoCom@Senai")
                    .password("adminConselho@estudante.com")
                    .build();

            service.editName(id, name, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita o email de um orientador", description = "Edita o email de um orientador e retorna o orientador editado com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "orientador editado com sucesso",
            content = @Content(schema = @Schema(implementation = AdvisorResponseDTO.class),
                    examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar orientador")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editEmail/{id}")
    public ResponseEntity<AdvisorResponseDTO> editEmail(
            @Parameter (description = "ID do orientador a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Novo email do orientador", required = true) String email/*,
        @RequestParam @Parameter(description = "Usuário que editou o orientador", required = true) User actor*/){

        try {

            Admin actor = Admin.builder()
                    .id(1L)
                    .username("adminTrabalhandoCom@Senai")
                    .password("adminConselho@estudante.com")
                    .build();

            return new ResponseEntity<>(service.editEmail(id, email, actor), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita o registro de um orientador", description = "Edita o registro de um orientador e retorna o orientador editado com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "orientador editado com sucesso",
            content = @Content(schema = @Schema(implementation = AdvisorResponseDTO.class),
                    examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar orientador")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editRegister/{id}")
    public ResponseEntity<AdvisorResponseDTO> editRegister(
            @Parameter (description = "ID do orientador a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Novo registro do orientador", required = true) Long register/*,
            @RequestParam @Parameter(description = "Usuário que editou o orientador", required = true) User actor*/) {

        try{

            Admin actor = Admin.builder()
                    .id(1L)
                    .username("adminTrabalhandoCom@Senai")
                    .password("adminConselho@estudante.com")
                    .build();

            return new ResponseEntity<>(service.editRegister(id, register, actor), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita a senha de um orientador", description = "Edita a senha de um orientador e retorna o orientador editado com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "orientador editado com sucesso",
            content = @Content(schema = @Schema(implementation = AdvisorResponseDTO.class),
                    examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar orientador")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editPassword/{id}")
    public ResponseEntity<AdvisorResponseDTO> editPassword(
            @Parameter (description = "ID do orientador a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Nova senha do orientador", required = true) String password/*,
            @RequestParam @Parameter(description = "Usuário que editou o orientador", required = true) User actor*/) {

        try{

            Admin actor = Admin.builder()
                    .id(1L)
                    .username("adminTrabalhandoCom@Senai")
                    .password("adminConselho@estudante.com")
                    .build();

            return new ResponseEntity<>(service.editPassword(id, password, actor), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita a imagem de um orientador", description = "Edita a imagem de um orientador e retorna o orientador editado com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "orientador editado com sucesso",
            content = @Content(schema = @Schema(implementation = AdvisorResponseDTO.class),
                    examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar orientador")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editImage/{id}")
    public ResponseEntity<AdvisorResponseDTO> editImage(
            @Parameter (description = "ID do orientador a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Nova imagem do orientador", required = true) String image/*,
            @RequestParam @Parameter(description = "Usuário que editou o orientador", required = true) User actor*/) {

        try{

            Admin actor = Admin.builder()
                    .id(1L)
                    .username("adminTrabalhandoCom@Senai")
                    .password("adminConselho@estudante.com")
                    .build();

            return new ResponseEntity<>(service.editImage(id, image, actor), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca uma lista paginada de todas as tecnicas", description = "Busca uma lista paginada de todas as tecnicas e retorna com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "orientador encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = AdvisorResponseDTO.class),
                    examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao encontrar orientador")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping
    public ResponseEntity<Page<AdvisorResponseDTO>> findAdvisors(
            @Parameter(description = "Busca uma lista paginada de todas as tecnicas", content =
            @Content(schema = @Schema(implementation = AdvisorResponseDTO.class)),
                    example = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")
            @PageableDefault( page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        try {
            return new ResponseEntity<>(service.findAdvisors(pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca um orientador pelo ID", description = "Busca um orientador pelo ID e retorna com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "orientador encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = AdvisorResponseDTO.class),
                    examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao encontrar orientador")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/{id}")
    public ResponseEntity<AdvisorResponseDTO> getAdvisorById(
            @Parameter(description = "Busca um orientador pelo ID", required = true, example = "1") @PathVariable Long id) {

        try {
            return new ResponseEntity<>(service.findAdvisorById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Adiciona uma notificação a um orientador", description = "Adiciona uma notificação a um orientador e retorna com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "Notificação adicionada com sucesso",
            content = @Content(schema = @Schema(implementation = AdvisorResponseDTO.class),
                    examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar notificação")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/addNotification/{id}")
    public ResponseEntity<AdvisorResponseDTO> addNotification(
            @Parameter (description = "ID do orientador", required = true) @PathVariable Long id,
            @Parameter (description = "Notificação a ser adicionada", required = true) @RequestBody Notification notification) {

        try {
            AdvisorResponseDTO response = service.addNotification(id, notification);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Remove uma notificação de um orientador", description = "Remove uma notificação de um orientador e retorna com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "Notificação removida com sucesso",
            content = @Content(schema = @Schema(implementation = AdvisorResponseDTO.class),
                    examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao remover notificação")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/removeNnotification/{id}")
    public ResponseEntity<AdvisorResponseDTO> removeNotification(
            @Parameter(description = "ID do orientador", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "Notificação a ser removida", required = true) @RequestBody Notification notification) {
        AdvisorResponseDTO response = service.removeNotification(id, notification);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove um orientador", description = "Remove um orientador e retorna com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "orientador removida com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao remover orientador")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvisor(
            @Parameter(description = "ID do orientador a ser deletado", required = true) @PathVariable Long id/*,
            @Parameter(description = "Usuário que deletou o orientador", required = true) @RequestParam User actor*/) {

        try {

            Admin actor = Admin.builder()
                    .id(1L)
                    .username("adminTrabalhandoCom@Senai")
                    .password("adminConselho@estudante.com")
                    .build();

            service.delete(id, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
