package conselho.estudante.com.projetoconselho.SERVICES.USERS;

import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS.TeacherRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.TeacherResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Course;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Shift;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Subject;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.*;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.USERS.TeacherRepository;
import conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION.CourseService;
import conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION.SUBJECT.SubjectService;
import conselho.estudante.com.projetoconselho.SERVICES.LOGS.UserLogsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
@AllArgsConstructor
public class TeacherService {

    private final TeacherRepository repository;
    private final UserLogsService logsService;
    private final CourseService courseService;
    private final SubjectService subjectService;
    private static final int passwordLength = 8;

    public TeacherResponseDTO create(TeacherRequestDTO teacherRequestDTO, User actor) {
        Teacher teacher = teacherRequestDTO.convert();
        teacher.setCreatedAt(new Date());
        teacher.setPassword(generateRandomPassword());

        if (repository.existsByEmail(teacher.getEmail())) {
            throw new DadosDuplicadosException("Email já cadastrado");
        }
        if (repository.existsByRegister(teacher.getRegister())) {
            throw new DadosDuplicadosException("Registro já cadastrado");
        }

        logsService.create(actor, teacher, "create");
        return repository.save(teacher).toDTO();
    }

    public TeacherResponseDTO update(Long id, TeacherRequestDTO teacherRequestDTO, User actor) {
        Teacher teacher = teacherRequestDTO.convert();
        if (!repository.existsById(id)) {
            throw new NaoEncontradoException("Professor não encontrado");
        }

        teacher.setId(id);
        if (repository.existsByEmailAndIdNot(teacher.getEmail(), id)) {
            throw new DadosDuplicadosException("Email já cadastrado");
        }
        if (repository.existsByRegisterAndIdNot(teacher.getRegister(), id)) {
            throw new DadosDuplicadosException("Registro já cadastrado");
        }

        Teacher oldTeacher = repository.findById(id).get();
        List<EditableItem> changes = getEditableItems(oldTeacher, teacher);
        logsService.create(actor, teacher, changes, "update");

        return repository.save(teacher).toDTO();
    }

    public TeacherResponseDTO editName(Long id, String name, User actor) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));
        String oldName = teacher.getName();
        logsService.create(actor, teacher,
                Collections.singletonList(new ChangeItem("name", oldName, name)), "update");
        teacher.setName(name);
        return repository.save(teacher).toDTO();
    }

    public TeacherResponseDTO editEmail(Long id, String email, User actor) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));
        String oldEmail = teacher.getEmail();
        logsService.create(actor, teacher,
                Collections.singletonList(new ChangeItem("email", oldEmail, email)), "update");
        teacher.setEmail(email);
        return repository.save(teacher).toDTO();
    }

    public TeacherResponseDTO editRegister(Long id, Long register, User actor) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));
        Long oldRegister = teacher.getRegister();
        logsService.create(actor, teacher,
                Collections.singletonList(new ChangeItem("register", oldRegister, register)), "update");
        teacher.setRegister(register);
        return repository.save(teacher).toDTO();
    }

    public TeacherResponseDTO editPassword(Long id, String password, User actor) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));
        String oldPassword = teacher.getPassword();
        logsService.create(actor, teacher,
                Collections.singletonList(new ChangeItem("password", oldPassword, password)), "update");
        teacher.setPassword(password);
        return repository.save(teacher).toDTO();
    }

    public TeacherResponseDTO editImage(Long id, String image, User actor) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));
        String oldImage = teacher.getImage();
        logsService.create(actor, teacher,
                Collections.singletonList(new ChangeItem("image", oldImage, image)), "update");
        teacher.setImage(image);
        return repository.save(teacher).toDTO();
    }

    public Page<TeacherResponseDTO> findAll(Pageable pageable) {
        try {
            return repository.findAll(pageable).map(Teacher::toDTO);
        } catch (Exception e) {
            throw new NaoEncontradoException("Professores não encontrados");
        }
    }

    public Page<TeacherResponseDTO> findByCourse(Course course, Pageable pageable) {
        try {
            return repository.findAllByCourses(course, pageable).map(Teacher::toDTO);
        } catch (Exception e) {
            throw new NaoEncontradoException("Professores não encontrados para este curso");
        }
    }

    public Page<TeacherResponseDTO> findBySubject(Subject subject, Pageable pageable) {
        try {
            return repository.findAllBySubjects(subject, pageable).map(Teacher::toDTO);
        } catch (Exception e) {
            throw new NaoEncontradoException("Professores não encontrados para esta disciplina");
        }
    }

    public Page<TeacherResponseDTO> findByShift(Shift shift, Pageable pageable) {
        try {
            return repository.findAllByShifts(shift, pageable).map(Teacher::toDTO);
        } catch (Exception e) {
            throw new NaoEncontradoException("Professores não encontrados para este turno");
        }
    }

    public TeacherResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(Teacher::toDTO)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));
    }

    public TeacherResponseDTO findByEmail(String email) {
        Teacher teacher = repository.findByEmail(email);
        if (teacher == null) {
            throw new NaoEncontradoException("Professor não encontrado");
        }
        return teacher.toDTO();
    }

    public TeacherResponseDTO addCourse(Long teacherId, Course course, User actor) {
        Teacher teacher = repository.findById(teacherId)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));

        if (teacher.addCourse(course)) {
            logsService.create(actor, teacher,
                    Collections.singletonList(new AddItem("courses", course)), "add");
            courseService.addTeacherToCourse(course, teacher, actor);
            return repository.save(teacher).toDTO();
        }
        throw new DadosDuplicadosException("Curso já adicionado ao professor");
    }

    public TeacherResponseDTO removeCourse(Long teacherId, Course course, User actor) {
        Teacher teacher = repository.findById(teacherId)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));

        if (teacher.removeCourse(course)) {
            logsService.create(actor, teacher,
                    Collections.singletonList(new AddItem("courses", course)), "remove");
            courseService.removeTeacherFromCourse(course, teacher, actor);
            return repository.save(teacher).toDTO();
        }
        throw new NaoEncontradoException("Curso não encontrado na lista do professor");
    }

    public TeacherResponseDTO addSubject(Long teacherId, Subject subject, User actor) {
        Teacher teacher = repository.findById(teacherId)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));

        if (teacher.addSubject(subject)) {
            logsService.create(actor, teacher,
                    Collections.singletonList(new AddItem("subjects", subject)), "add");
            subjectService.addTeacherToSubject(subject, teacher, actor);
            return repository.save(teacher).toDTO();
        }
        throw new DadosDuplicadosException("Disciplina já adicionada ao professor");
    }

    public TeacherResponseDTO removeSubject(Long teacherId, Subject subject, User actor) {
        Teacher teacher = repository.findById(teacherId)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));

        if (teacher.removeSubject(subject)) {
            logsService.create(actor, teacher,
                    Collections.singletonList(new AddItem("subjects", subject)), "remove");
            subjectService.removeTeacherFromSubject(subject, teacher, actor);
            return repository.save(teacher).toDTO();
        }
        throw new NaoEncontradoException("Disciplina não encontrada na lista do professor");
    }

    public TeacherResponseDTO addShift(Long teacherId, Shift shift, User actor) {
        Teacher teacher = repository.findById(teacherId)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));

        if (teacher.addShift(shift)) {
            logsService.create(actor, teacher,
                    Collections.singletonList(new AddItem("shifts", shift)), "add");
            return repository.save(teacher).toDTO();
        }
        throw new DadosDuplicadosException("Turno já adicionado ao professor");
    }

    public TeacherResponseDTO removeShift(Long teacherId, Shift shift, User actor) {
        Teacher teacher = repository.findById(teacherId)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));

        if (teacher.removeShift(shift)) {
            logsService.create(actor, teacher,
                    Collections.singletonList(new AddItem("shifts", shift)), "remove");
            return repository.save(teacher).toDTO();
        }
        throw new NaoEncontradoException("Turno não encontrado na lista do professor");
    }

    public void delete(Long id, User actor) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));
        repository.deleteById(id);
        logsService.create(actor, teacher, "delete");
    }

    private List<EditableItem> getEditableItems(Teacher oldTeacher, Teacher teacher) {
        List<EditableItem> changes = new ArrayList<>();

        if (!oldTeacher.getName().equals(teacher.getName())) {
            changes.add(new ChangeItem("name", oldTeacher.getName(), teacher.getName()));
        }
        if (!oldTeacher.getEmail().equals(teacher.getEmail())) {
            changes.add(new ChangeItem("email", oldTeacher.getEmail(), teacher.getEmail()));
        }
        if (!oldTeacher.getRegister().equals(teacher.getRegister())) {
            changes.add(new ChangeItem("register", oldTeacher.getRegister(), teacher.getRegister()));
        }
        if (!oldTeacher.getPassword().equals(teacher.getPassword())) {
            changes.add(new ChangeItem("password", oldTeacher.getPassword(), teacher.getPassword()));
        }
        if (!Objects.equals(oldTeacher.getImage(), teacher.getImage())) {
            changes.add(new ChangeItem("image", oldTeacher.getImage(), teacher.getImage()));
        }

        return changes;
    }

    private String generateRandomPassword() {
        final String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
        SecureRandom random = new SecureRandom();
        StringBuilder senha = new StringBuilder();
        for (int i = 0; i < passwordLength; i++) {
            int index = random.nextInt(caracteres.length());
            senha.append(caracteres.charAt(index));
        }
        return senha.toString();
    }

    public Teacher findObjectTeacher(String email) {
        try {
            return repository.findByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }

    public Teacher findObjectTeacher(Long id) {
        try {
            return repository.findById(id).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}