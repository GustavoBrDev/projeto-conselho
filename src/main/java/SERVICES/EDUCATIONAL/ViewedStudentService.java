package SERVICES.EDUCATIONAL;

import MODELS.ENTITY.EDUCATIONAL.ViewedStudents;
import MODELS.ENTITY.EDUCATIONAL.Council;
import MODELS.ENTITY.EDUCATIONAL.Student;
import REPOSITORIES.EDUCATIONAL.ViewedStudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por gerenciar as operações relacionadas a {@link ViewedStudents}.
 * Esta classe permite a criação, obtenção e manipulação de alunos visualizados dentro do {@link Council}.
 *
 * @author Cauã Justimiano Dutra
 * @see ViewedStudents
 * @see Council
 * @see ViewedStudentsRepository
 * @since 17/03/2025
 */
@Service
public class ViewedStudentService {

    @Autowired
    private ViewedStudentsRepository repository;

    /**
     * Obtém a entidade {@link ViewedStudents} associada a um determinado {@link Council}.
     *
     * @param council o conselho educacional ao qual os {@link ViewedStudents} pertencem.
     * @return a entidade {@link ViewedStudents} encontrada ou null caso não exista.
     * @see ViewedStudents
     * @since 17/03/2025
     */
    public ViewedStudents getViewedStudentsByCouncil(Council council) {
        return repository.findByCouncil(council);
    }

    /**
     * Cria uma nova entidade {@link ViewedStudents} associada a um determinado {@link Council}.
     *
     * @param council o conselho educacional para o qual os {@link ViewedStudents} serão criados.
     * @return a entidade {@link ViewedStudents} recém-criada e persistida no banco de dados.
     * @see ViewedStudents
     * @since 17/03/2025
     */
    public ViewedStudents createViewedStudents(Council council) {
        ViewedStudents viewedStudents = new ViewedStudents();
        viewedStudents.setCouncil(council);
        return repository.save(viewedStudents);
    }

    /**
     * Adiciona um aluno à lista de {@link ViewedStudents} de um determinado {@link Council}.
     *
     * @param council o conselho educacional ao qual o aluno será adicionado.
     * @param student o aluno a ser adicionado.
     * @return true se o aluno foi adicionado com sucesso, false caso contrário.
     * @see Student
     * @see ViewedStudents
     * @since 17/03/2025
     */
    public boolean addStudentToViewed(Council council, Student student) {
        ViewedStudents viewedStudents = getViewedStudentsByCouncil(council);
        if (viewedStudents == null) {
            return false;
        }
        boolean added = viewedStudents.addStudent(student);
        if (added) {
            repository.save(viewedStudents);
        }
        return added;
    }
}
