package conselho.estudante.com.projetoconselho.services.users;


import conselho.estudante.com.projetoconselho.models.dto.request.users.TeacherRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.users.TeacherResponseDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.users.TeacherResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Course;
import conselho.estudante.com.projetoconselho.models.entity.administration.Notification;
import conselho.estudante.com.projetoconselho.models.entity.administration.Shift;
import conselho.estudante.com.projetoconselho.models.entity.administration.Subject;
import conselho.estudante.com.projetoconselho.models.entity.logs.*;
import conselho.estudante.com.projetoconselho.models.entity.users.Teacher;
import conselho.estudante.com.projetoconselho.models.entity.users.Teacher;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.models.exceptions.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.users.TeacherRepository;
import conselho.estudante.com.projetoconselho.services.administration.CourseService;
import conselho.estudante.com.projetoconselho.services.administration.subject.SubjectService;
import conselho.estudante.com.projetoconselho.services.EmailService;
import conselho.estudante.com.projetoconselho.services.logs.UserLogsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;


import java.security.SecureRandom;
import java.util.*;


/**
 * Classe de serviços da entidade Teacher
 * @author Alex Zastrow
 * @author Gustavo Stinghen (documentação)
 * @since 28/03/2025
 */

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class TeacherService {

    private TeacherRepository repository;
    private UserLogsService logsService;
    private CourseService courseService;
    @Lazy
    private SubjectService subjectService;
    private EmailService emailService;
    private static final int passwordLength = 8;


    /**
     * Cria um professor
     * @param teacherRequestDTO DTO contendo os dados do professor
     * @param actor Usuário que adicionou o professor
     * @return DTO do professor criado
     *
     * Atualizado em 31/03/2025
     * Adicionado envio de email
     */
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

        emailService.sendWelcomeEmail(teacher.getEmail(), teacher.getPassword());
        teacher = repository.save(teacher);
        logsService.create(actor, teacher, "create");
        return teacher.toDTO();
    }


    /**
     * Atualiza um professor
     * @param id ID do professor
     * @param teacherRequestDTO DTO contendo os novos dados do professor
     * @param actor Usuário que editou o professor
     * @return DTO do professor atualizado
     */
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
        teacher.setCreatedAt(oldTeacher.getCreatedAt());
        List<EditableItem> changes = getEditableItems(oldTeacher, teacher);
        logsService.create(actor, teacher, changes, "update");


        return repository.save(teacher).toDTO();
    }

    /**
     * Método para editar o nome de um professor
     * @param id o id do professor
     * @param name o novo nome
     * @param actor o usuario que editou
     * @return o professor editado
     */
    public TeacherResponseDTO editName(Long id, String name, User actor) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));
        String oldName = teacher.getName();
        logsService.create(actor, teacher,
                Collections.singletonList(new ChangeItem("name", oldName, name)), "update");
        teacher.setName(name);
        return repository.save(teacher).toDTO();
    }

    /**
     * Método para editar o email de um professor
     * @param id o id do professor
     * @param email o novo email
     * @param actor o usuario que editou
     * @return o professor editado
     */
    public TeacherResponseDTO editEmail(Long id, String email, User actor) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));
        String oldEmail = teacher.getEmail();
        logsService.create(actor, teacher,
                Collections.singletonList(new ChangeItem("email", oldEmail, email)), "update");
        teacher.setEmail(email);
        return repository.save(teacher).toDTO();
    }

    /**
     * Método para editar o registro de um professor
     * @param id o id do professor
     * @param register o novo registro
     * @param actor o usuario que editou
     * @return o professor editado
     */
    public TeacherResponseDTO editRegister(Long id, Long register, User actor) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));
        Long oldRegister = teacher.getRegister();
        logsService.create(actor, teacher,
                Collections.singletonList(new ChangeItem("register", oldRegister, register)), "update");
        teacher.setRegister(register);
        return repository.save(teacher).toDTO();
    }

    /**
     * Método para editar a senha de um professor
     * @param id o id do professor
     * @param password a nova senha
     * @param actor o usuario que editou
     * @return o professor editado
     */
    public TeacherResponseDTO editPassword(Long id, String password, User actor) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));
        String oldPassword = teacher.getPassword();
        logsService.create(actor, teacher,
                Collections.singletonList(new ChangeItem("password", oldPassword, password)), "update");
        teacher.setPassword(password);
        return repository.save(teacher).toDTO();
    }

    /**
     * Método para editar a imagem de um professor
     * @param id o id do professor
     * @param image a nova imagem
     * @param actor o usuario que editou
     * @return o professor editado
     */
    public TeacherResponseDTO editImage(Long id, String image, User actor) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));
        String oldImage = teacher.getImage();
        logsService.create(actor, teacher,
                Collections.singletonList(new ChangeItem("image", oldImage, image)), "update");
        teacher.setImage(image);
        return repository.save(teacher).toDTO();
    }

    /**
     * Método para listar todos os professores
     * @param pageable Objeto que contém informações de paginação (tamanho e número da página).
     * @return Página contendo os professores
     */
    public Page<TeacherResponseDTO> findAll(Pageable pageable) {
        try {
            return repository.findAll(pageable).map(Teacher::toDTO);
        } catch (Exception e) {
            throw new NaoEncontradoException("Professores não encontrados");
        }
    }

    /**
     * Método para listar todos os professores de um curso
     * @param course o curso
     * @param pageable Objeto que contém informações de paginação (tamanho e número da página).
     * @return Página contendo os professores
     */
    public Page<TeacherResponseDTO> findByCourse(Course course, Pageable pageable) {
        try {
            return repository.findAllByCourses(course, pageable).map(Teacher::toDTO);
        } catch (Exception e) {
            throw new NaoEncontradoException("Professores não encontrados para este curso");
        }
    }

    /**
     * Método para listar todos os professores de uma disciplina
     * @param subject a disciplina
     * @param pageable Objeto que contém informações de paginação (tamanho e número da página).
     * @return Página contendo os professores
     */
    public Page<TeacherResponseDTO> findBySubject(Subject subject, Pageable pageable) {
        try {
            return repository.findAllBySubjects(subject, pageable).map(Teacher::toDTO);
        } catch (Exception e) {
            throw new NaoEncontradoException("Professores não encontrados para esta disciplina");
        }
    }

    /**
     * Método para listar todos os professores de um turno
     * @param shift o turno
     * @param pageable Objeto que contém informações de paginação (tamanho e número da página).
     * @return Página contendo os professores
     */
    public Page<TeacherResponseDTO> findByShift(Shift shift, Pageable pageable) {
        try {
            return repository.findAllByShifts(shift, pageable).map(Teacher::toDTO);
        } catch (Exception e) {
            throw new NaoEncontradoException("Professores não encontrados para este turno");
        }
    }

    /**
     * Método para buscar um professor pelo ID
     * @param id o id do professor
     * @return o professor encontrado
     */
    public TeacherResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(Teacher::toDTO)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));
    }

    /**
     * Método para buscar um professor pelo email
     * @param email o email do professor
     * @return o professor encontrado
     */
    public TeacherResponseDTO findByEmail(String email) {
        Teacher teacher = repository.findByEmail(email);
        if (teacher == null) {
            throw new NaoEncontradoException("Professor não encontrado");
        }
        return teacher.toDTO();
    }

    /**
     * Método para adicionar um curso ao professor
     * @param teacherId o id do professor
     * @param course o curso a ser adicionado
     * @param actor o usuario que adicionou
     * @return o professor com o curso adicionado
     */
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

    /**
     * Método para remover um curso do professor
     * @param teacherId o id do professor
     * @param course o curso a ser removido
     * @param actor o usuario que removeu
     * @return o professor com o curso removido
     */
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

    /**
     * Método para adicionar uma disciplina ao professor
     * @param teacherId o id do professor
     * @param subject a disciplina a ser adicionada
     * @param actor o usuario que adicionou
     * @return o professor com a disciplina adicionada
     */
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

    /**
     * Método para remover uma disciplina do professor
     * @param teacherId o id do professor
     * @param subject a disciplina a ser removida
     * @param actor o usuario que removeu
     * @return o professor com a disciplina removida
     */
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

    /**
     * Método para adicionar um turno ao professor
     * @param teacherId o id do professor
     * @param shift o turno a ser adicionado
     * @param actor o usuario que adicionou
     * @return o professor com o turno adicionado
     */
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

    /**
     * Método para remover um turno do professor
     * @param teacherId o id do professor
     * @param shift o turno a ser removido
     * @param actor o usuario que removeu
     * @return o professor com o turno removido
     */
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

    /**
     * Método para deletar um professor
     * @param id o id do professor
     * @param actor o usuario que deletou
     */
    public void delete(Long id, User actor) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Professor não encontrado"));
        repository.deleteById(id);
        logsService.create(actor, teacher, "delete");
    }

    /**
     * Método auxiliar para gerar logs
     * @param oldTeacher o professor antigo
     * @param teacher o professor novo
     * @return a lista de itens editados
     */
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

    /**
     * Método auxiliar para gerar uma senha aleatória com o tamanho especificado.
     * @return uma String com a senha gerada
     */
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

    /**
     * Método para buscar um professor pelo email
     * @param email o email do professor
     * @return o professor encontrado
     */
    public Teacher getObjectTeacher(String email) {
        try {
            return repository.findByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Método para buscar um professor pelo id
     * @param id o id do professor
     * @return o professor encontrado
     */
    public Teacher getObjectTeacher(Long id) {
        try {
            return repository.findById(id).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Método para editar a senha de um professor
     * @param user o professor a ser editado
     * @param password a nova senha
     * @return um booleano indicando se a edição foi bem sucedida
     */
    public boolean editPassword(Teacher user, String password) {

        try {
            user.setPassword(password);
            repository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Adiciona uma {@link Notification} a um {@link Teacher}.
     * @param id o identificador do teacher
     * @param notification a notificação a ser adicionada
     * @return {@link TeacherResponseDTO} o teacher atualizado
     * @throws NaoEncontradoException se o teacher não for encontrado
     */
    public TeacherResponseDTO addNotification(Long id, Notification notification) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Teacher não encontrado"));

        if ( ! teacher.addNotification(notification) ) {
            throw new NaoEncontradoException("Notificação nao encontrada");
        }

        logsService.create(  teacher, Collections.singletonList( new AddItem("notifications", (Object) notification ) ), "add" );
        return repository.save(teacher).toDTO();
    }

    /**
     * Remove uma {@link Notification} de um {@link Teacher}.
     * @param id o identificador do teacher
     * @param notification a notificação a ser removida
     * @return {@link TeacherResponseDTO} o teacher atualizado
     * @throws NaoEncontradoException se o teacher não for encontrado
     */
    public TeacherResponseDTO removeNotification(Long id, Notification notification) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Teacher não encontrado"));

        if ( ! teacher.removeNotification(notification) ) {
            throw new NaoEncontradoException("Notificação nao encontrada");
        }

        logsService.create( teacher, Collections.singletonList( new AddItem("notifications", (Object) notification ) ), "remove" );
        return repository.save(teacher).toDTO();
    }
}
