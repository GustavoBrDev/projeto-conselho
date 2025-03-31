package conselho.estudante.com.projetoconselho.Controller.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.ADMINISTRATION.ClasseRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.ADMINISTRATION.ClasseResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Course;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION.ClasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gerenciar as operações relacionadas a Classe.
 * Realiza a exposição das operações da ClasseService via API REST.
 *
 * @author joana voigt
 * @since 18/03/2025
 * @updated em 25/03/2025
 */
@RestController
@RequestMapping("/api/classes")
public class ClasseController {

    private final ClasseService classeService;

    @Autowired
    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    /**
     * Endpoint para criar uma nova turma.
     *
     * @param classeRequestDTO Dados para criação de uma turma.
     * @param actor Usuário que está criando a turma.
     * @return ClasseResponseDTO com as informações da turma criada.
     */
    @PostMapping
    public ClasseResponseDTO create(@RequestBody ClasseRequestDTO classeRequestDTO, @RequestHeader("user") User actor) {
        return classeService.create(classeRequestDTO, actor);
    }

    /**
     * Endpoint para atualizar uma turma existente.
     *
     * @param id ID da turma que deve ser atualizada.
     * @param classeRequestDTO Dados para atualizar a turma.
     * @param actor Usuário que está atualizando a turma.
     * @return ClasseResponseDTO com as informações da turma atualizada.
     */
    @PutMapping("/{id}")
    public ClasseResponseDTO update(@PathVariable Long id, @RequestBody ClasseRequestDTO classeRequestDTO, @RequestHeader("user") User actor) {
        return classeService.update(id, classeRequestDTO, actor);
    }

    /**
     * Endpoint para editar o nome de uma turma existente.
     *
     * @param id ID da turma a ser editada.
     * @param name Novo nome para a turma.
     * @param actor Usuário que está editando a turma.
     * @return ClasseResponseDTO com as informações da turma editada.
     */
    @PatchMapping("/{id}/name")
    public ClasseResponseDTO editName(@PathVariable Long id, @RequestParam String name, @RequestHeader("user") User actor) {
        return classeService.editName(id, name, actor);
    }

    /**
     * Endpoint para editar a sigla de uma turma existente.
     *
     * @param id ID da turma a ser editada.
     * @param acronym Nova sigla para a turma.
     * @param actor Usuário que está editando a turma.
     * @return ClasseResponseDTO com as informações da turma editada.
     */
    @PatchMapping("/{id}/acronym")
    public ClasseResponseDTO editAcronym(@PathVariable Long id, @RequestParam String acronym, @RequestHeader("user") User actor) {
        return classeService.editAcronym(id, acronym, actor);
    }

    /**
     * Endpoint para editar o curso associado a uma turma existente.
     *
     * @param id ID da turma a ser editada.
     * @param course Novo curso a ser associado à turma.
     * @param actor Usuário que está editando a turma.
     * @return ClasseResponseDTO com as informações da turma editada.
     */
    @PatchMapping("/{id}/course")
    public ClasseResponseDTO editCourse(@PathVariable Long id, @RequestBody Course course, @RequestHeader("user") User actor) {
        return classeService.editCourse(id, course, actor);
    }

    /**
     * Endpoint para editar o status ativo de uma turma.
     *
     * @param id ID da turma a ser editada.
     * @param active Novo status ativo para a turma.
     * @param actor Usuário que está editando a turma.
     * @return ClasseResponseDTO com as informações da turma editada.
     */
    @PatchMapping("/{id}/active")
    public ClasseResponseDTO editActive(@PathVariable Long id, @RequestParam boolean active, @RequestHeader("user") User actor) {
        return classeService.editActive(id, active, actor);
    }

    /**
     * Endpoint para buscar uma lista paginada de turmas.
     *
     * @param pageable Objeto de paginação.
     * @return Uma página de turmas.
     */
    @GetMapping
    public Page<ClasseResponseDTO> findClasses(Pageable pageable) {
        return classeService.findClasses(pageable);
    }

    /**
     * Endpoint para buscar uma turma pelo seu ID.
     *
     * @param id ID da turma a ser buscada.
     * @return ClasseResponseDTO com as informações da turma encontrada.
     */
    @GetMapping("/{id}")
    public ClasseResponseDTO findById(@PathVariable Long id) {
        return classeService.findById(id);
    }

    /**
     * Endpoint para adicionar um aluno a uma turma.
     *
     * @param id ID da turma a ser modificada.
     * @param student Aluno a ser adicionado.
     * @param actor Usuário que está adicionando o aluno.
     */
    @PostMapping("/{id}/students")
    public void addStudentToClasse(@PathVariable Long id, @RequestBody Student student, @RequestHeader("user") User actor) {
        Classe classe = classeService.findById(id);
        classeService.addStudentToClasse(classe, student, actor);
    }

    /**
     * Endpoint para remover um aluno de uma turma.
     *
     * @param id ID da turma a ser modificada.
     * @param student Aluno a ser removido.
     * @param actor Usuário que está removendo o aluno.
     */
    @DeleteMapping("/{id}/students")
    public void removeStudentFromClasse(@PathVariable Long id, @RequestBody Student student, @RequestHeader("user") User actor) {
        Classe classe = classeService.findById(id);
        classeService.removeStudentFromClasse(classe, student, actor);
    }

    /**
     * Endpoint para deletar uma turma.
     *
     * @param id ID da turma a ser deletada.
     * @param actor Usuário que está deletando a turma.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestHeader("user") User actor) {
        classeService.delete(id, actor);
    }
}
