package conselho.estudante.com.projetoconselho.Controller;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.LOGIN.FirstLoginResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.LOGIN.LoginResponse;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.LOGIN.LoginResponseDTO;
import conselho.estudante.com.projetoconselho.SERVICES.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe de controller da entidade Login
 * @author Gustavo Stinghen
 * @since 31/03/2025
 */

@RestController
@AllArgsConstructor
@Tag(name = "Login", description = "Operações de login de usuarios")
public class LoginController {

    private LoginService service;

    @Operation(summary = "Realiza o login de um usuario", description = "Realiza o login de um usuario e retorna um token de autenticação para o mesmo")
    @ApiResponse(responseCode = "200", description = "Primeiro login realizado com sucesso",
            content = @Content(schema = @Schema(implementation = FirstLoginResponseDTO.class),
                    examples = @ExampleObject(
                            value = "{\"user\": {\"id\": 1, \"name\": \"João\"}, \"isFirstLogin\": true, \"isAuthenticated\": true}"
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
            content = @Content(schema = @Schema(implementation = LoginResponseDTO.class),
                    examples = @ExampleObject(
                            value = "{\"user\": {\"id\": 1, \"name\": \"João\"}, \"isFirstLogin\": false, \"isAuthenticated\": true}"
                    )
            )
    )
    @ApiResponse(responseCode = "400", description = "Login falhou")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Parameter (description = "Email do usuario", required = true, example = "jao@gmail") @RequestParam String email,
            @Parameter (description = "Senha do usuario", required = true, example = "123456") @RequestParam String password) {

        try {
            return ResponseEntity.ok(service.login(email, password));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Realiza o login de um usuario", description = "Realiza o login de um usuario e retorna um token de autenticação para o mesmo")
    @ApiResponse(responseCode = "200", description = "Email enviado com sucesso")
    @ApiResponse(responseCode = "400", description = "Email nao enviado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/resetPassword")
    public ResponseEntity<Boolean> resetPassword(
            @Parameter (description = "Email do usuario", required = true, example = "jao@gmail") @RequestParam String email) {

        try {
            return ResponseEntity.ok(service.resetPassword(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Realiza o login de um usuario", description = "Realiza o login de um usuario e retorna um token de autenticação para o mesmo")
    @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso")
    @ApiResponse(responseCode = "400", description = "Senha nao alterada")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/changePassword")
    public ResponseEntity<Boolean> changePassword(
            @Parameter (description = "Email do usuario", required = true, example = "jao@gmail") @RequestParam String email,
            @Parameter (description = "Senha do usuario", required = true, example = "123456") @RequestParam String password) {

        try {
            return ResponseEntity.ok(service.changePassword(email, password));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Realiza o login de um usuario", description = "Realiza o login de um usuario e retorna um token de autenticação para o mesmo")
    @ApiResponse(responseCode = "200", description = "Token gerado com sucesso")
    @ApiResponse(responseCode = "400", description = "Token nao gerado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/firstLogin")
    public ResponseEntity<String> firstLogin(
            @Parameter (description = "Email do usuario", required = true, example = "jao@gmail") @RequestParam String email) {

        try {
            return ResponseEntity.ok(service.firstLogin(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Realiza o login de um usuario", description = "Realiza o login de um usuario e retorna um token de autenticação para o mesmo")
    @ApiResponse(responseCode = "200", description = "Token valido com sucesso")
    @ApiResponse(responseCode = "400", description = "Token nao valido")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(
            @Parameter (description = "Token do usuario", required = true, example = "jao@gmail") @RequestParam String token) {

        try {
            return ResponseEntity.ok(service.verifyToken(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
