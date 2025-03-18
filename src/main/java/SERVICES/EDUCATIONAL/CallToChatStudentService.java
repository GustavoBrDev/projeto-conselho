package SERVICES.EDUCATIONAL;

import MODELS.ENTITY.EDUCATIONAL.CallToChatStudents;
import MODELS.ENTITY.EDUCATIONAL.Council;
import MODELS.ENTITY.EDUCATIONAL.Student;
import REPOSITORIES.EDUCATIONAL.CallToChatStudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por gerenciar as operações relacionadas a CallToChatStudents.
 * Esta classe permite a criação, obtenção e manipulação de alunos dentro do CallToChatStudents associado a um {@link Council}.
 *
 * @author Cauã Justimiano Dutra
 * @see CallToChatStudents
 * @see Council
 * @see CallToChatStudentsRepository
 * @since 17/03/2025
 */
@Service
public class CallToChatStudentService {

    @Autowired
    private CallToChatStudentsRepository repository;

    /**
     * Obtém uma entidade CallToChatStudents associada a um determinado Council.
     *
     * @param council o conselho educacional ao qual os CallToChatStudents pertencem.
     * @return a entidade CallToChatStudents encontrada ou null caso não exista.
     * @see CallToChatStudents
     * @since 17/03/2025
     */
    public CallToChatStudents getCallToChatStudentsByCouncil(Council council) {
        return repository.findByCouncil(council);
    }

    /**
     * Cria uma nova entidade CallToChatStudents associada a um determinado Council.
     *
     * @param council o conselho educacional para o qual os CallToChatStudents serão criados.
     * @return a entidade CallToChatStudents recém-criada e persistida no banco de dados.
     * @see CallToChatStudents
     * @since 17/03/2025
     */
    public CallToChatStudents createCallToChatStudents(Council council) {
        CallToChatStudents callToChatStudents = new CallToChatStudents();
        callToChatStudents.setCouncil(council);
        return repository.save(callToChatStudents);
    }

    /**
     * Adiciona um aluno à lista de CallToChatStudents de um determinado Council.
     *
     * @param council o conselho educacional ao qual o aluno será adicionado.
     * @param student o aluno a ser adicionado.
     * @return true se o aluno foi adicionado com sucesso, false caso contrário.
     * @see Student
     * @see CallToChatStudents
     * @since 17/03/2025
     */
    public boolean addStudentToCallToChat(Council council, Student student) {
        CallToChatStudents callToChatStudents = getCallToChatStudentsByCouncil(council);
        if (callToChatStudents == null) {
            return false;
        }
        boolean added = callToChatStudents.addStudent(student);
        if (added) {
            repository.save(callToChatStudents);
        }
        return added;
    }

    /**
     * Remove um aluno da lista de CallToChatStudents de um determinado Council.
     *
     * @param council o conselho educacional ao qual o aluno está associado.
     * @param student o aluno a ser removido.
     * @return true se o aluno foi removido com sucesso, false caso contrário.
     * @see Student
     * @see CallToChatStudents
     * @since 17/03/2025
     */
    public boolean removeStudentFromCallToChat(Council council, Student student) {
        CallToChatStudents callToChatStudents = getCallToChatStudentsByCouncil(council);
        if (callToChatStudents == null) {
            return false;
        }
        boolean removed = callToChatStudents.removeStudent(student);
        if (removed) {
            repository.save(callToChatStudents);
        }
        return removed;
    }

    /**
     * Lista todos os CallToChatStudents com suporte a paginação.
     *
     * @param pageable objeto de paginação e ordenação.
     * @return uma página contendo CallToChatStudents.
     * @see CallToChatStudents
     * @since 17/03/2025
     */
    public Page<CallToChatStudents> listAllCallToChatStudents(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Deleta um CallToChatStudents associado a um determinado Council.
     *
     * @param council o conselho educacional ao qual os CallToChatStudents pertencem.
     * @see CallToChatStudents
     * @since 17/03/2025
     */

    /**
     * Lista todos os estudantes de CallToChatStudents com paginação.
     *
     * @param pageable informações de paginação.
     * @return uma página contendo os estudantes.
     * @see Student
     * @since 17/03/2025
     */
    public Page<Student> listAllStudents(Pageable pageable) {
        return repository.findAllStudents(pageable);
    }

    public void deleteCallToChatStudents(Council council) {
        CallToChatStudents callToChatStudents = getCallToChatStudentsByCouncil(council);
        if (callToChatStudents != null) {
            repository.delete(callToChatStudents);
        }
    }
}