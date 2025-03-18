package SERVICES.ADMINISTRATION;

import MODELS.DTO.REQUEST.ClasseRequestDTO;
import MODELS.DTO.RESPONSE.ClasseResponseDTO;
import MODELS.ENTITY.ADMINISTRATION.Classe;
import MODELS.ENTITY.ADMINISTRATION.Course;
import MODELS.EXCEPTIONS.DadosDuplicadosException;
import MODELS.EXCEPTIONS.NaoEncontradoException;
import REPOSITORIES.ADMINISTRATION.ClasseRepository;
import REPOSITORIES.ADMINISTRATION.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por gerenciar as operações relacionadas a Classe.
 *
 * @author joana voigt
 * @since 18/03/2025
 *
 * @see Classe
 * @see ClasseRepository
 */
@Service
@AllArgsConstructor
public class ClasseService {

    private ClasseRepository repository;

    /**
     * Cria uma nova turma a partir dos dados fornecidos em ClasseRequestDTO.
     *
     * @param classeRequestDTO Dados de solicitação para criar uma nova turma.
     * @return Uma representação DTO da turma recém-criada.
     * @throws DadosDuplicadosException se já existir uma turma ou sigla com o mesmo nome.
     */
    public ClasseResponseDTO create(ClasseRequestDTO classeRequestDTO) {
        Classe classe = classeRequestDTO.convert();
        if(repository.existsByName(classe.getName())) {
            throw new DadosDuplicadosException("Turma ja cadastrada");
        } else if (repository.existsByAcronym(classe.getAcronym())) {
            throw new DadosDuplicadosException("Sigla ja cadastrada");
        } else {
            return repository.save(classe).toDTO();
        }
    }

    /**
     * Atualiza uma turma existente com base no ID fornecido.
     *
     * @param id ID da turma que deve ser atualizada.
     * @param classeRequestDTO Dados para atualizar a turma.
     * @return Uma representação DTO da turma atualizada.
     * @throws DadosDuplicadosException se uma turma ou sigla semelhante já existir.
     * @throws NaoEncontradoException se a turma não for encontrada pelo ID.
     */
    public ClasseResponseDTO update(Long id, ClasseRequestDTO classeRequestDTO) {
        Classe classe = classeRequestDTO.convert();
        if (repository.existsById(id)) {
            classe.setId(id);
            if (repository.existsByName(classe.getName())) {
                throw new DadosDuplicadosException("Turma ja cadastrada");
            } else if (repository.existsByAcronym(classe.getAcronym())) {
                throw new DadosDuplicadosException("Sigla ja cadastrada");
            }
            return repository.save(classe).toDTO();
        }
        throw new DadosDuplicadosException("Turma nao encontrada");
    }

    /**
     * Edita o nome de uma turma existente.
     *
     * @param id ID da turma a ser editada.
     * @param name Novo nome para a turma.
     * @return Uma representação DTO da turma após a edição.
     */
    public ClasseResponseDTO editName(Long id, String name) {
        Classe classe = repository.findById(id).get();
        classe.setName(name);
        return repository.save(classe).toDTO();
    }

    /**
     * Edita a sigla de uma turma existente.
     *
     * @param id ID da turma a ser editada.
     * @param acronym Nova sigla para a turma.
     * @return Uma representação DTO da turma após a edição.
     */
    public ClasseResponseDTO editAcronym(Long id, String acronym) {
        Classe classe = repository.findById(id).get();
        classe.setAcronym(acronym);
        return repository.save(classe).toDTO();
    }

    /**
     * Edita o curso associado a uma turma existente.
     *
     * @param id ID da turma a ser editada.
     * @param course Novo curso a ser associado à turma.
     * @return Uma representação DTO da turma após a edição.
     */
    public ClasseResponseDTO editCourse(Long id, Course course) {
        Classe classe = repository.findById(id).get();
        classe.setCourse(course);
        return repository.save(classe).toDTO();
    }

    /**
     * Ativa ou desativa uma turma.
     *
     * @param id ID da turma a ser editada.
     * @param active Estado ativo da turma.
     * @return Uma representação DTO da turma após a edição.
     */
    public ClasseResponseDTO editActive(Long id, boolean active) {
        Classe classe = repository.findById(id).get();
        classe.setActive(active);
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
     * @param classe A turma à qual o aluno será adicionado.
     * @param student O aluno a ser adicionado.
     * @return Uma representação DTO da turma após a adição do aluno.
     * @throws NaoEncontradoException se o aluno não for encontrado ou não puder ser adicionado.
     */
    public ClasseResponseDTO addStudentToClasse(Classe classe, Student student){
        if (classe.addStudent(student)) {
            return repository.save(classe).toDTO();
        } else {
            throw new NaoEncontradoException("Aluno nao encontrado");
        }
    }

    /**
     * Remove um aluno de uma turma especificada.
     *
     * @param classe A turma da qual o aluno será removido.
     * @param student O aluno a ser removido.
     * @return Uma representação DTO da turma após a remoção do aluno.
     * @throws NaoEncontradoException se o aluno não for encontrado ou não puder ser removido.
     */
    public ClasseResponseDTO removeStudentFromClasse(Classe classe, Student student){
        if (classe.removeStudent(student)) {
            return repository.save(classe).toDTO();
        } else {
            throw new NaoEncontradoException("Aluno nao encontrado");
        }
    }
}
