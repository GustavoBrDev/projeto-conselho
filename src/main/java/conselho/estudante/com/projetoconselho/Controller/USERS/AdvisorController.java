package conselho.estudante.com.projetoconselho.Controller.USERS;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS.AdvisorRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.AdvisorResponseDTO;
import conselho.estudante.com.projetoconselho.SERVICES.USERS.AdvisorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Classe de controller da entidade Advisor
 * autor Alex Zastrow
 */

@RestController
@RequestMapping("/users/advisors")
@Tag(name = "Advisor", description = "Recurso para gerenciamento de orientadores")
@AllArgsConstructor
public class AdvisorController {

    private AdvisorService advisorService;

    /*
     * Criar Advisor
     */
    @PostMapping
    public ResponseEntity<AdvisorResponseDTO> criarAdvisor(@RequestBody AdvisorRequestDTO dto) {
        AdvisorResponseDTO advisorResponse = advisorService.create(dto);
        return ResponseEntity.ok(advisorResponse);
    }

    /*
     * Atualizar Advisor
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdvisorResponseDTO> atualizarAdvisor(@PathVariable Long id, @RequestBody AdvisorRequestDTO dto) {
        AdvisorResponseDTO updatedAdvisor = advisorService.update(id, dto);
        return ResponseEntity.ok(updatedAdvisor);
    }

    /*
     * Buscar Advisor por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdvisorResponseDTO> buscarPorId(@PathVariable Long id) {
        AdvisorResponseDTO advisor = advisorService.findAdvisorById(id);
        return ResponseEntity.ok(advisor);
    }
}
