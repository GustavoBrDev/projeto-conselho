package conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION;

import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Subject;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.EDUCATIONAL.AvaliableTeacher;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.ADMINISTRATION.AvaliableTeacherRepository;
import conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION.SUBJECT.SubjectService;
import conselho.estudante.com.projetoconselho.SERVICES.USERS.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de serviços da entidade AvaliableTeacher
 * @author Gustavo Stinghen
 * @since 26/03/2025
 * @see AvaliableTeacher
 */

@AllArgsConstructor
@Service
public class AvaliableTeacherService {

    private AvaliableTeacherRepository repository;
    private SubjectService subjectService;
    private TeacherService teacherService;

    /**
     * Método para criar um AvaliableTeacher
     * @param teacherId o ID do professor
     * @param subjectIds os IDs das matérias
     * @return true se o AvaliableTeacher foi criado, false caso contrário
     */
    public boolean create(Long teacherId, List<Long> subjectIds) {

        if (teacherId == null || subjectIds == null) {
            return false;
        }

        List<Subject> subjects = new ArrayList<>();

        for ( Long subjectId : subjectIds ) {

            Subject subject = subjectService.getObjectSubject(subjectId);

            if (subject == null) {
                return false;
            }

            subjects.add(subject);
        }

        Teacher teacher = teacherService.getObjectTeacher(teacherId);

        if (teacher == null) {
            return false;
        }

        AvaliableTeacher avaliableTeacher = AvaliableTeacher.builder()
                .teacher(teacher)
                .subjects(subjects)
                .build();

        repository.save(avaliableTeacher);

        return true;

    }

    /**
     * Método para atualizar um AvaliableTeacher
     * @param avaliableTeacherId o ID do AvaliableTeacher
     * @param teacherId o ID do professor
     * @param subjectIds os IDs das matérias
     * @return true se o AvaliableTeacher foi atualizado, false caso contrário
     */
    public boolean update(Long avaliableTeacherId, Long teacherId, List<Long> subjectIds) {

        if (avaliableTeacherId == null || teacherId == null || subjectIds == null) {
            return false;
        } else {

            AvaliableTeacher avaliableTeacher = repository.findById(avaliableTeacherId).orElse(null);

            if (avaliableTeacher == null) {
                return false;
            }

            List<Subject> subjects = new ArrayList<>();

            for ( Long subjectId : subjectIds ) {

                Subject subject = subjectService.getObjectSubject(subjectId);

                if (subject == null) {
                    return false;
                }

                subjects.add(subject);
            }

            Teacher teacher = teacherService.getObjectTeacher(teacherId);

            if (teacher == null) {
                return false;
            }

            avaliableTeacher.setTeacher(teacher);
            avaliableTeacher.setSubjects(subjects);

            repository.save(avaliableTeacher);

            return true;
        }
    }

    /**
     * Método para buscar todos os AvaliableTeachers
     * @return uma lista de AvaliableTeachers
     */
    public List<AvaliableTeacher> getAllAvaliableTeachers() {

        return repository.findAll();
    }

    /**
     * Método para buscar um AvaliableTeacher
     * @param avaliableTeacherId o ID do AvaliableTeacher
     * @return o AvaliableTeacher
     */
    public AvaliableTeacher getObjectAvaliableTeacher(Long avaliableTeacherId) {

        if ( ! repository.existsById(avaliableTeacherId) ) {
            throw new NaoEncontradoException("Registro nao encontrado");
        }

        return repository.findById(avaliableTeacherId).orElse(null);
    }

    /**
     * Método para deletar um AvaliableTeacher
     * @param avaliableTeacherId o ID do AvaliableTeacher
     * @return true se o AvaliableTeacher foi deletado, false caso contrário
     */
    public boolean delete(Long avaliableTeacherId) {

        try {

            if ( ! repository.existsById(avaliableTeacherId) ) {
                throw new NaoEncontradoException("Professor nao encontrado");
            }

            repository.deleteById(avaliableTeacherId);
            return true;
        } catch (Exception e) {
            throw new NaoEncontradoException("Professor nao encontrado");
        }
    }
}
