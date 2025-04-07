package conselho.estudante.com.projetoconselho.services.administration;

import conselho.estudante.com.projetoconselho.models.dto.request.ADMINISTRATION.ClasseRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.ADMINISTRATION.ClasseResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Classe;
import conselho.estudante.com.projetoconselho.models.entity.administration.Course;
import conselho.estudante.com.projetoconselho.models.entity.logs.AddItem;
import conselho.estudante.com.projetoconselho.models.entity.logs.ChangeItem;
import conselho.estudante.com.projetoconselho.models.entity.logs.EditableItem;
import conselho.estudante.com.projetoconselho.models.entity.users.Student;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.models.exceptions.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.administration.ClasseRepository;
import conselho.estudante.com.projetoconselho.services.logs.ClassLogsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Serviço responsável por gerenciar as operações relacionadas a Classe.
 *
 * @author joana voigt
 * @since 18/03/2025
 *
 * @see Classe
 * @see ClasseRepository
 *
 * Atualizado em 25/03/2025
 * Conexão com o ClasseLogsService para gerar logs
 * @author Gustavo Stinghen
 * @see ClassLogsService
 */
@Service
@AllArgsConstructor
public class ClasseService {


    private ClasseRepository repository;
    private ClassLogsService logsService;
    private CourseService courseService;

    /**
     * Cria uma nova turma a partir dos dados fornecidos em ClasseRequestDTO.
     *
     * @param classeRequestDTO Dados de solicitação para criar uma nova turma.
     * @param actor Usuário ator da solicitação.
     * @return Uma representação DTO da turma recém-criada.
     * @throws DadosDuplicadosException se já existir uma turma ou sigla com o mesmo nome.
     */
    public ClasseResponseDTO create(ClasseRequestDTO classeRequestDTO, User actor) {
        Classe classe = classeRequestDTO.convert();
        if(repository.existsByName(classe.getName())) {
            throw new DadosDuplicadosException("Turma ja cadastrada");
        } else if (repository.existsByAcronym(classe.getAcronym())) {
            throw new DadosDuplicadosException("Sigla ja cadastrada");
        } else {
            courseService.addClassToCourse(classe.getCourse(), classe, actor);
            logsService.create(actor, classe, "create");
            return repository.save(classe).toDTO();
        }
    }


    /**
     * Atualiza uma turma existente com base no ID fornecido.
     *
     * @param id ID da turma que deve ser atualizada.
     * @param classeRequestDTO Dados para atualizar a turma.
     * @param actor Usuário ator da solicitação.
     * @return Uma representação DTO da turma atualizada.
     * @throws DadosDuplicadosException se uma turma ou sigla semelhante já existir.
     */
    public ClasseResponseDTO update(Long id, ClasseRequestDTO classeRequestDTO, User actor) {
        Classe classe = classeRequestDTO.convert();
        if (repository.existsById(id)) {
            classe.setId(id);
            if (repository.existsByName(classe.getName())) {
                throw new DadosDuplicadosException("Turma ja cadastrada");
            } else if (repository.existsByAcronym(classe.getAcronym())) {
                throw new DadosDuplicadosException("Sigla ja cadastrada");
            }
            logsService.create(actor, classe, getEditableItems(repository.findById(id).get(), classe, actor), "update");
            return repository.save(classe).toDTO();
        }
        throw new DadosDuplicadosException("Turma nao encontrada");
    }

    /**
     * Método auxiliar para gerar logs que mostra os campos que foram editados
     * @param oldClasse a turma antiga
     * @param newClasse a turma nova
     * @param actor o usuário que editou
     * @return uma lista com os campos que foram editados
     * @author Gustavo Stinghen
     * @since 25/03/2024
     */
    private List<EditableItem> getEditableItems(Classe oldClasse, Classe newClasse, User actor) {

        List<EditableItem> changes = new ArrayList<>();

        if (! oldClasse.getName().equals(newClasse.getName())) {
            changes.add(new ChangeItem("name", oldClasse.getName(), newClasse.getName()));
        }
        if (! oldClasse.getAcronym().equals(newClasse.getAcronym())) {
            changes.add(new ChangeItem("acronym", oldClasse.getAcronym(), newClasse.getAcronym()));
        }
        if (oldClasse.getCourse() != newClasse.getCourse()) {
            changes.add(new ChangeItem("course", oldClasse.getCourse(), newClasse.getCourse()));
            courseService.removeClassFromCourse(oldClasse.getCourse(), oldClasse, actor);
            courseService.addClassToCourse(newClasse.getCourse(), newClasse, actor);
        }
        if ( oldClasse.isActive() != newClasse.isActive() ) {
            changes.add(new ChangeItem("active", oldClasse.isActive(), newClasse.isActive()));
        }

        return changes;
    }


    /**
     * Edita o nome de uma turma existente.
     *
     * @param id ID da turma a ser editada.
     * @param name Novo nome para a turma.
     * @param actor Usuário ator da solicitação.
     * @return Uma representação DTO da turma após a edição.
     */
    public ClasseResponseDTO editName(Long id, String name, User actor) {
        Classe classe = repository.findById(id).get();
        String oldName = classe.getName();
        classe.setName(name);
        logsService.create(actor, classe, Collections.singletonList(new ChangeItem("name", (Object) oldName, (Object) name)), "update");
        return repository.save(classe).toDTO();
    }


    /**
     * Edita a sigla de uma turma existente.
     *
     * @param id ID da turma a ser editada.
     * @param acronym Nova sigla para a turma.
     * @param actor Usuário ator da solicitação.
     * @return Uma representação DTO da turma após a edição.
     */
    public ClasseResponseDTO editAcronym(Long id, String acronym, User actor) {
        Classe classe = repository.findById(id).get();
        String oldAcronym = classe.getAcronym();
        classe.setAcronym(acronym);
        logsService.create(actor, classe, Collections.singletonList(new ChangeItem("acronym", (Object) oldAcronym, (Object) acronym)), "update");
        return repository.save(classe).toDTO();
    }


    /**
     * Edita o curso associado a uma turma existente.
     *
     * @param id ID da turma a ser editada.
     * @param course Novo curso a ser associado à turma.
     * @param actor Usuário ator da solicitação.
     * @return Uma representação DTO da turma após a edição.
     */
    public ClasseResponseDTO editCourse(Long id, Course course, User actor) {
        Classe classe = repository.findById(id).get();
        Course oldCourse = classe.getCourse();
        classe.setCourse(course);
        courseService.addClassToCourse(course, classe, actor);
        courseService.removeClassFromCourse(oldCourse, classe, actor);
        logsService.create(actor, classe, Collections.singletonList(new ChangeItem("course", (Object) oldCourse, (Object) course)), "update");
        return repository.save(classe).toDTO();
    }

    /**
     * Ativa ou desativa uma turma.
     *
     * @param id ID da turma a ser editada.
     * @param active Estado ativo da turma.
     * @param actor Usuário ator da solicitação.
     * @return Uma representação DTO da turma após a edição.
     */
    public ClasseResponseDTO editActive(Long id, boolean active, User actor) {
        Classe classe = repository.findById(id).get();
        boolean oldActive = classe.isActive();
        classe.setActive(active);
        logsService.create(actor, classe, Collections.singletonList(new ChangeItem("active", (Object) oldActive, (Object) active)), "update");
        return repository.save(classe).toDTO();
    }

    /**
     * Busca uma lista paginada de todas as turmas.
     *
     * @param pageable Objeto de paginação.
     * @return Uma página de turmas representadas como ClasseResponseDTO.
     * @throws NaoEncontradoException se nenhuma turma for encontrada.
     */
    public Page<ClasseResponseDTO> findClasses(Pageable pageable) {
        try {
            return repository.findAll(pageable).map(Classe::toDTO);
        } catch (Exception e) {
            throw new NaoEncontradoException("Turma nao encontrada");
        }
    }


    /**
     * Busca uma lista paginada de turmas associadas a um curso específico.
     *
     * @param course O curso cujas turmas devem ser buscadas.
     * @param pageable Objeto de paginação.
     * @return Uma página de turmas representadas como ClasseResponseDTO.
     * @throws NaoEncontradoException se nenhuma turma for encontrada para o curso.
     */
    public Page<ClasseResponseDTO> findClassesByCourse (Course course, Pageable pageable) {
        try {
            return repository.findAllByCourse(course, pageable).map(Classe::toDTO);
        } catch (Exception e){
            throw new NaoEncontradoException("Turmas não encontradas");
        }
    }


    /**
     * Encontra uma turma pelo seu ID.
     *
     * @param id ID da turma.
     * @return Uma representação DTO da turma encontrada.
     * @throws NaoEncontradoException se nenhuma turma for encontrada pelo ID.
     */
    public ClasseResponseDTO findById(Long id) {
        try {
            return repository.findById(id).get().toDTO();
        } catch (Exception e) {
            throw new NaoEncontradoException("Turma nao encontrada");
        }
    }


    /**
     * Adiciona um aluno a uma turma especificada.
     *
     * @param classe  A turma à qual o aluno será adicionado.
     * @param student O aluno a ser adicionado.
     * @param actor   Usuário ator da solicitação.
     * @throws NaoEncontradoException se o aluno não for encontrado ou não puder ser adicionado.
     */
    public void addStudentToClasse(Classe classe, Student student, User actor){
        if (classe.addStudent(student)) {
            logsService.create(actor, classe, Collections.singletonList(new AddItem("students", (Object) student)), "add");
            repository.save(classe).toDTO();
        } else {
            throw new NaoEncontradoException("Aluno nao encontrado");
        }
    }


    /**
     * Remove um aluno de uma turma especificada.
     *
     * @param classe  A turma da qual o aluno será removido.
     * @param student O aluno a ser removido.
     * @param actor   Usuário ator da solicitação.
     * @throws NaoEncontradoException se o aluno não for encontrado ou não puder ser removido.
     */
    public void removeStudentFromClasse(Classe classe, Student student, User actor){
        if (classe.removeStudent(student)) {
            logsService.create(actor, classe, Collections.singletonList(new AddItem("students", (Object) student)), "remove");
            repository.save(classe).toDTO();
        } else {
            throw new NaoEncontradoException("Aluno nao encontrado");
        }
    }


    /**
     * Deleta uma turma pelo seu ID.
     *
     * @param id ID da turma a ser deletada.
     * @param actor Usuário que deletou a turma.
     * @throws NaoEncontradoException se a turma não for deletada corretamente.
     */
    public void delete(Long id, User actor) {
        try {

            if ( repository.existsById(id) ) {
                logsService.create(actor, repository.findById(id).get(), "delete");
                repository.deleteById(id);
            }

        } catch (Exception e) {
            throw new NaoEncontradoException("Turma nao deletada");
        }
    }

    public Classe findObjectClasse(Long id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            throw null;
        }
    }
}

