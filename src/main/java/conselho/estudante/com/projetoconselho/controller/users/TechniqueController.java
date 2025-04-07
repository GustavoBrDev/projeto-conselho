package conselho.estudante.com.projetoconselho.controller.users;

import conselho.estudante.com.projetoconselho.models.dto.request.USERS.TechniqueRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.USERS.TechniqueResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Notification;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.services.users.TECHNIQUE.TechniqueService;
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

@RestController
@RequestMapping("/USERS/Technique")
@AllArgsConstructor
@Tag(name = "Technique", description = "Recurso para gerenciamento de tecnicos")
public class TechniqueController {
    private TechniqueService service;

    @Operation(summary = "Cria um tecnico", description = "Cria um tecnico e retorna o tecnico criado com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "Tecnico criado com sucesso",
            content = @Content(schema = @Schema(implementation = TechniqueResponseDTO.class),
            examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao criar tecnico")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PostMapping
    public ResponseEntity<TechniqueResponseDTO> postTechnique(
            @Parameter(description = "Tecnico a ser criado", content =
            @Content (schema = @Schema(implementation = TechniqueRequestDTO.class)),
            required = true, example = "{" +
            "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")
            @RequestBody @Valid TechniqueRequestDTO techniqueRequestDTO,
            @RequestParam @Parameter(description = "Usuário que criou o tecnico", required = true) User actor) {

        try {
            return new ResponseEntity<>(service.create(techniqueRequestDTO, actor), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita um tecnico", description = "Edita um tecnico e retorna o tecnico editado com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "Tecnico editado com sucesso",
            content = @Content(schema = @Schema(implementation = TechniqueResponseDTO.class),
                    examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar tecnico")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PutMapping("/{id}")
    public ResponseEntity<TechniqueResponseDTO> putTechnique(
            @Parameter(description = "Tecnico a ser editado", content =
            @Content(schema = @Schema(implementation = TechniqueRequestDTO.class)),
            required = true, example = "{" +
                    "\"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")
            @RequestBody @Valid TechniqueRequestDTO techniqueRequestDTO,
            @Parameter(description = "ID do tecnico a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Usuário que editou o tecnico", required = true) User actor) {

        try {
            return new ResponseEntity<>(service.update(id, techniqueRequestDTO, actor), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita o nome de um tecnico", description = "Edita o nome de um tecnico e retorna o tecnico editado com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "Tecnico editado com sucesso",
            content = @Content(schema = @Schema(implementation = TechniqueResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Robson\", \"email\": \"roberto123@gmail\",  \"createdAt\": \"2023-01-01\", \"register\": \"12345\", \"notifications\": []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar tecnico")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editName/{id}")
    public ResponseEntity<String> editName(
        @Parameter (description = "ID do tecnico a ser editado", required = true) @PathVariable Long id,
        @RequestParam @Parameter(description = "Novo nome do tecnico", required = true) String name,
        @RequestParam @Parameter(description = "Usuário que editou o tecnico", required = true) User actor){

        try {
            service.editName(id, name, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita o email de um tecnico", description = "Edita o email de um tecnico e retorna o tecnico editado com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "Tecnico editado com sucesso",
            content = @Content(schema = @Schema(implementation = TechniqueResponseDTO.class),
            examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar tecnico")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editEmail/{id}")
    public ResponseEntity<String> editEmail(
        @Parameter (description = "ID do tecnico a ser editado", required = true) @PathVariable Long id,
        @RequestParam @Parameter(description = "Novo email do tecnico", required = true) String email,
        @RequestParam @Parameter(description = "Usuário que editou o tecnico", required = true) User actor){

        try {
            service.editEmail(id, email, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita o registro de um tecnico", description = "Edita o registro de um tecnico e retorna o tecnico editado com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "Tecnico editado com sucesso",
            content = @Content(schema = @Schema(implementation = TechniqueResponseDTO.class),
            examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar tecnico")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editRegister/{id}")
    public ResponseEntity<String> editRegister(
            @Parameter (description = "ID do tecnico a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Novo registro do tecnico", required = true) Long register,
            @RequestParam @Parameter(description = "Usuário que editou o tecnico", required = true) User actor) {

        try{
            service.editRegister(id, register, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita a senha de um tecnico", description = "Edita a senha de um tecnico e retorna o tecnico editado com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "Tecnico editado com sucesso",
            content = @Content(schema = @Schema(implementation = TechniqueResponseDTO.class),
                    examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar tecnico")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editPassword/{id}")
    public ResponseEntity<String> editPassword(
            @Parameter (description = "ID do tecnico a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Nova senha do tecnico", required = true) String password,
            @RequestParam @Parameter(description = "Usuário que editou o tecnico", required = true) User actor) {

        try{
            service.editPassword(id, password, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita a imagem de um tecnico", description = "Edita a imagem de um tecnico e retorna o tecnico editado com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "Tecnico editado com sucesso",
            content = @Content(schema = @Schema(implementation = TechniqueResponseDTO.class),
                    examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao editar tecnico")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editImage/{id}")
    public ResponseEntity<String> editImage(
            @Parameter (description = "ID do tecnico a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Nova imagem do tecnico", required = true) String image,
            @RequestParam @Parameter(description = "Usuário que editou o tecnico", required = true) User actor) {

        try{
            service.editImage(id, image, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca uma lista paginada de todas as tecnicas", description = "Busca uma lista paginada de todas as tecnicas e retorna com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "Tecnico encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = TechniqueResponseDTO.class),
                    examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao encontrar tecnico")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/allTechniques")
    public ResponseEntity<Page<TechniqueResponseDTO>> findTechniques(
            @Parameter(description = "Busca uma lista paginada de todas as tecnicas", content =
            @Content(schema = @Schema(implementation = TechniqueResponseDTO.class)),
            example = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")
        @PageableDefault( page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC)Pageable pageable) {

        try {
            return new ResponseEntity<>(service.findTechniques(pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca um tecnico pelo ID", description = "Busca um tecnico pelo ID e retorna com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "Tecnico encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = TechniqueResponseDTO.class),
                    examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao encontrar tecnico")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/{id}")
    public ResponseEntity<TechniqueResponseDTO> getTechniqueById(
            @Parameter(description = "Busca um tecnico pelo ID", required = true, example = "1") @PathVariable Long id) {

        try {
            return new ResponseEntity<>(service.findTechniqueById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca um tecnico pelo email", description = "Busca um tecnico pelo email e retorna com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "Tecnico encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = TechniqueResponseDTO.class),
                    examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao encontrar tecnico")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/{email}")
    public ResponseEntity<TechniqueResponseDTO> getTechniqueByEmail(
            @Parameter(description = "Busca um tecnico pelo email", required = true, example = "roberto@gmail.com") @PathVariable String email) {

        try {
            return new ResponseEntity<>(service.findTechniqueByEmail(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Adiciona uma notificação a um tecnico", description = "Adiciona uma notificação a um tecnico e retorna com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "Notificação adicionada com sucesso",
            content = @Content(schema = @Schema(implementation = TechniqueResponseDTO.class),
                    examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar notificação")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/notifications/{id}")
    public ResponseEntity<TechniqueResponseDTO> addNotification(
            @Parameter (description = "ID do tecnico", required = true) @PathVariable Long id,
            @Parameter (description = "Notificação a ser adicionada", required = true) @RequestBody Notification notification) {

       try {
           TechniqueResponseDTO response = service.addNotification(id, notification);
           return ResponseEntity.ok(response);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    @Operation(summary = "Remove uma notificação de um técnico", description = "Remove uma notificação de um técnico e retorna com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "Notificação removida com sucesso",
            content = @Content(schema = @Schema(implementation = TechniqueResponseDTO.class),
                    examples = @ExampleObject(value = "\"id\" : 1, \"image\" : \"imagem\", \"name\" : \"Roberto\", \"username\" : \"roberto\", \"email\" : \"roberto@gmail.com\", \"password\" : \"senha123\", \"createdAt\" : \"2023-01-01\", \"register\" : \"12345\", \"notifications\" : []}")))
    @ApiResponse(responseCode = "400", description = "Erro ao remover notificação")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/{id}/notifications/{notification}")
    public ResponseEntity<TechniqueResponseDTO> removeNotification(
            @Parameter(description = "ID do técnico", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "Notificação a ser removida", required = true) @PathVariable Notification notification) {
        TechniqueResponseDTO response = service.removeNotification(id, notification);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove um técnico", description = "Remove um técnico e retorna com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "Técnico removida com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao remover técnico")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnique(
            @Parameter(description = "ID do tecnico a ser deletado", required = true) @PathVariable Long id,
            @Parameter(description = "Usuário que deletou o tecnico", required = true) @RequestParam User actor) {

        try {
            service.delete(id, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
