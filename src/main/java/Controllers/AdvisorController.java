package Controllers;

import MODELS.ENTITY.DTO.REQUEST.AdvisorRequestDTO;
import MODELS.ENTITY.DTO.RESPONSE.AdvisorResponseDTO;
import MODELS.ENTITY.USERS.Advisor;
import Services.AdvisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Classe controller para o advisor
 * author Alex Zastrow
 */

@RestController
@RequestMapping("/advisors")
public class AdvisorController {

    @Autowired
    private AdvisorService advisorService;

    /*
     * Criar novo orientador (POST)
     * @param advisor
     */
    @PostMapping
    public AdvisorResponseDTO criarAdvisor(@RequestBody AdvisorRequestDTO advisorRequestDTO) {
        return advisorService.criarAdvisor(advisorRequestDTO);
    }

    /*
     * Atualizar orientador (PUT)
     * @param id
     * @param novosDados
     */
    @PutMapping("/{id}")
    public AdvisorResponseDTO atualizarAdvisor(@PathVariable Long id, @RequestBody AdvisorRequestDTO advisorRequestDTO) {
        return advisorService.atualizarAdvisor(id, advisorRequestDTO);
    }

    /*
     * Editar nome (PATCH)
     * @param id
     * @param novoNome
     */
    @PatchMapping("/{id}/nome")
    public AdvisorResponseDTO editarNome(@PathVariable Long id, @RequestParam String nome) {
        return advisorService.editarNome(id, nome);
    }

    /*
     * Listar todos os orientadores (GET com paginação)
     */
    @GetMapping
    public Page<AdvisorResponseDTO> listarTodos(Pageable pageable) {
        return advisorService.listarTodos(pageable);
    }

    /*
     * Buscar orientador por ID (GET)
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAdvisor(@PathVariable Long id) {
        advisorService.deletarAdvisor(id);
        return ResponseEntity.ok().build();
    }
}