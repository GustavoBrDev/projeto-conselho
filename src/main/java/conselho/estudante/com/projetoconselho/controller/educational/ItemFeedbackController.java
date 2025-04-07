package conselho.estudante.com.projetoconselho.CONTROLLERS.EDUCATIONAL;

import conselho.estudante.com.projetoconselho.models.dto.request.EDUCATIONAL.ItemFeedbackRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.EDUCATIONAL.ItemFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.services.educational.ItemFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Controlador REST para gerenciar feedbacks de itens.
 * Fornece operações CRUD básicas e filtragem por conselho.
 */

@RestController
@RequestMapping("educational/itemfeedback")
@RequiredArgsConstructor
public class ItemFeedbackController {

    private final ItemFeedbackService service;

    @PostMapping
    public ResponseEntity<ItemFeedbackResponseDTO> create(@RequestBody ItemFeedbackRequestDTO requestDTO) {
        return ResponseEntity.ok(service.create(requestDTO, null)); // Sem segurança, usuário será null
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemFeedbackResponseDTO> update(@PathVariable Long id, @RequestBody ItemFeedbackRequestDTO requestDTO) {
        return ResponseEntity.ok(service.update(id, requestDTO, null)); // Sem segurança
    }

    @GetMapping
    public ResponseEntity<Page<ItemFeedbackResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemFeedbackResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id, null); // Sem segurança
        return ResponseEntity.noContent().build();
    }
}
