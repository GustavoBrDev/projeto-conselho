package conselho.estudante.com.projetoconselho.controller.educational;

import conselho.estudante.com.projetoconselho.models.dto.request.EDUCATIONAL.PersonalFeedbackRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.EDUCATIONAL.PersonalFeedbackResponseDTO;
import conselho.estudante.com.projetoconselho.services.educational.PersonalFeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gerenciar feedbacks pessoais.
 * Fornece operações CRUD básicas para {@link PersonalFeedbackResponseDTO}.
 */
@RestController
@RequestMapping("/personal-feedbacks")
@AllArgsConstructor
public class PersonalFeedbackController {

    private final PersonalFeedbackService service;

    /**
     * Cria um novo feedback pessoal.
     * @param requestDTO Dados do feedback
     * @return Feedback criado
     */
    @PostMapping
    public ResponseEntity<PersonalFeedbackResponseDTO> create(@RequestBody PersonalFeedbackRequestDTO requestDTO) {
        return ResponseEntity.ok(service.create(requestDTO));
    }

    /**
     * Atualiza um feedback existente.
     * @param id ID do feedback
     * @param requestDTO Dados atualizados
     * @return Feedback atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<PersonalFeedbackResponseDTO> update(@PathVariable Long id, @RequestBody PersonalFeedbackRequestDTO requestDTO) {
        return ResponseEntity.ok(service.update(id, requestDTO));
    }

    /**
     * Edita o texto de um feedback específico.
     * @param id ID do feedback
     * @param text Novo texto do feedback
     * @return Feedback atualizado
     */
    @PatchMapping("/{id}/text")
    public ResponseEntity<PersonalFeedbackResponseDTO> editText(@PathVariable Long id, @RequestParam String text) {
        return ResponseEntity.ok(service.editText(id, text));
    }

    /**
     * Obtém uma lista paginada de todos os feedbacks.
     * @param pageable Configuração de paginação
     * @return Página de feedbacks
     */
    @GetMapping
    public ResponseEntity<Page<PersonalFeedbackResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Obtém uma lista paginada de feedbacks de um conselho específico.
     * @param councilId ID do conselho
     * @param pageable Configuração de paginação
     * @return Página de feedbacks do conselho
     */
    @GetMapping("/council/{councilId}")
    public ResponseEntity<Page<PersonalFeedbackResponseDTO>> findByCouncil(@PathVariable Long councilId, Pageable pageable) {
        return ResponseEntity.ok(service.findByCouncil(councilId, pageable));
    }

    /**
     * Busca um feedback pelo ID.
     * @param id ID do feedback
     * @return Feedback encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<PersonalFeedbackResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Deleta um feedback pelo ID.
     * @param id ID do feedback
     * @return Resposta sem conteúdo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
