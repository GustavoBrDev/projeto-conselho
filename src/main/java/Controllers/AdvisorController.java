package Controllers;

import MODELS.ENTITY.USERS.Advisor;
import Services.AdvisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/advisors")
public class AdvisorController {

    @Autowired
    private AdvisorService advisorService;

    @PostMapping
    public Advisor criarAdvisor(@RequestBody Advisor advisor) {
        return advisorService.criarAdvisor(advisor);
    }

    @PutMapping("/{id}")
    public Advisor atualizarAdvisor(@PathVariable Long id, @RequestBody Advisor advisor) {
        return advisorService.atualizarAdvisor(id, advisor);
    }

    @PatchMapping("/{id}/nome")
    public Advisor editarNome(@PathVariable Long id, @RequestParam String nome) {
        return advisorService.editarNome(id, nome);
    }

    @GetMapping
    public Page<Advisor> listarTodos(Pageable pageable) {
        return advisorService.listarTodos(pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAdvisor(@PathVariable Long id) {
        advisorService.deletarAdvisor(id);
        return ResponseEntity.ok().build();
    }
}