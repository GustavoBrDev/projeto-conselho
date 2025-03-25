package conselho.estudante.com.projetoconselho.SERVICES.USERS;


import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS.StudentRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.StudentResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Notification;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.AddItem;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.ChangeItem;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS.EditableItem;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.User;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.USERS.StudentRepository;
import conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION.ClasseService;
import conselho.estudante.com.projetoconselho.SERVICES.EmailService;
import conselho.estudante.com.projetoconselho.SERVICES.LOGS.UserLogsService;
import lombok.AllArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * Classe de serviços para a entidade Student
 * Responsável por operações de CRUD e manipulação de dados do aluno
 * Classe de servicos da entidade Student
 * @author Camilly Chelest
 * @since 12/03/2025
 *
 * Atualizado em 20/03/2025
 * Conexão com o UserLogsService para gerar logs
 * @author Gustavo Stinghen
 * @see UserLogsService
 */


@Service
@AllArgsConstructor
public class StudentService {


    private StudentRepository repository;
    private UserLogsService logsService;
    private EmailService emailService;
    private ClasseService classeService;

    private static final int passwordLength = 8;

    /**
     * Cria um novo {@link Student}
     * @param studentRequestDTO os dados do estudante a ser criado
     * @return {@link StudentResponseDTO} o estudante criado
     * @throws DadosDuplicadosException se o email ou matrícula já estiverem cadastrados
     *
     * Atualizado em 20/03/2025
     * Geração de senha aleatória
     * @author Gustavo Stinghen
     * @param actor o usuário que criou o estudante
     */
    public StudentResponseDTO create(StudentRequestDTO studentRequestDTO, User actor) {
        Student student = studentRequestDTO.convert();
        Date data = new Date();
        student.setCreatedAt(data);
        student.setPassword(generateRandomPassword());
        if (repository.existsByEmail(student.getEmail())) {
            throw new DadosDuplicadosException("Email ja cadastrado");
        } else if (repository.existsByRegistration(student.getRegistration())) {
            throw new DadosDuplicadosException("Matricula ja cadastrada");
        }

        logsService.create( actor, student, "create" );
        emailService.sendWelcomeEmail( student.getEmail(), student.getPassword() );
        return repository.save(student).convert();
    }

    /**
     * Método auxiliar para gerar uma senha aleatória com o tamanho especificado.
     * @return uma String com a senha gerada
     * @author Gustavo Stinghen
     * @since 20/03/2025
     * @see SecureRandom
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
     * Atualiza um {@link Student} existente
     * @param id o identificador do estudante
     * @param studentRequestDTO os novos dados do estudante
     * @param actor o usuário que atualizou o estudante
     * @return {@link StudentResponseDTO} o estudante atualizado
     * @throws NaoEncontradoException se o estudante não for encontrado
     */
    public StudentResponseDTO update(Long id, StudentRequestDTO studentRequestDTO, User actor) {
        Student student = studentRequestDTO.convert();
        if (repository.existsById(id)) {
            student.setId(id);
            if (repository.existsByEmail(student.getEmail())) {
                throw new DadosDuplicadosException("Email ja cadastrado");
            } else if (repository.existsByRegistration(student.getRegistration())) {
                throw new DadosDuplicadosException("Matricula ja cadastrada");
            }

            Student oldStudent = repository.findById(id).get();
            List<EditableItem> changes = getEditableItems(oldStudent, student);
            logsService.create( actor, student, changes, "update" );

            return repository.save(student).convert();
        }
        throw new NaoEncontradoException("Aluno nao encontrado");
    }

    /**
     * Monta uma lista de {@link EditableItem} com as alterações feitas em um {@link Student}
     * @param oldStudent o estudante antes das alterações
     * @param student o estudante depois das alterações
     * @return a lista de {@link EditableItem}
     */
    private static List<EditableItem> getEditableItems(Student oldStudent, Student student) {
        List<EditableItem> changes = new ArrayList<EditableItem>();

        if ( ! oldStudent.getName().equals( student.getName() ) ) {
            EditableItem name = new ChangeItem( "name", (Object) oldStudent.getName(), (Object) student.getName() );
            changes.add(name);
        }

        if ( ! oldStudent.getEmail().equals( student.getEmail() ) ) {
            EditableItem email = new ChangeItem( "email", (Object) oldStudent.getEmail(), (Object) student.getEmail() );
            changes.add(email);
        }

        if ( ! oldStudent.getRegistration().equals( student.getRegistration() ) ) {
            EditableItem registration = new ChangeItem( "registration", (Object) oldStudent.getRegistration(), (Object) student.getRegistration() );
            changes.add(registration);
        }

        if ( ! oldStudent.getPassword().equals( student.getPassword() ) ) {
            EditableItem password = new ChangeItem( "password", (Object) oldStudent.getPassword(), (Object) student.getPassword() );
            changes.add(password);
        }

        if ( ! oldStudent.getIsHidden() .equals( student.getIsHidden() ) ) {
            EditableItem isHidden = new ChangeItem( "isHidden", (Object) oldStudent.getIsHidden(), (Object) student.getIsHidden() );
            changes.add(isHidden);
        }

        if ( ! oldStudent.getIsRepresentative() .equals( student.getIsRepresentative() ) ) {
            EditableItem isRepresentative = new ChangeItem( "isRepresentative", (Object) oldStudent.getIsRepresentative(), (Object) student.getIsRepresentative() );
            changes.add(isRepresentative);
        }

        return changes;
    }


    /**
     * Edita o nome de um {@link Student}
     * @param id o identificador do estudante
     * @param name o novo nome do estudante
     * @param actor o usuário que atualizou o estudante
     * @return {@link StudentResponseDTO} o estudante atualizado
     */
    public StudentResponseDTO editName(Long id, String name, User actor) {
        Student student = repository.findById(id).get();
        String oldName = student.getName();
        logsService.create(actor, student, Collections.singletonList(new ChangeItem("name", (Object) oldName, (Object) name)), "update");
        student.setName(name);
        return repository.save(student).convert();
    }


    /**
     * Edita o email de um {@link Student}
     * @param id o identificador do estudante
     * @param email o novo email do estudante
     * @param actor o usuário que atualizou o estudante
     * @return {@link StudentResponseDTO} o estudante atualizado
     */
    public StudentResponseDTO editEmail(Long id, String email, User actor) {
        Student student = repository.findById(id).get();
        String oldEmail = student.getEmail();
        logsService.create(actor, student, Collections.singletonList(new ChangeItem("email", (Object) oldEmail, (Object) email)), "update");
        student.setEmail(email);
        return repository.save(student).convert();
    }


    /**
     * Edita a matrícula de um {@link Student}
     * @param id o identificador do estudante
     * @param registration a nova matrícula do estudante
     * @param actor o usuário que atualizou o estudante
     * @return {@link StudentResponseDTO} o estudante atualizado
     */
    public StudentResponseDTO editRegistration(Long id, Long registration, User actor) {
        Student student = repository.findById(id).get();
        Long oldRegistration = student.getRegistration();
        logsService.create(actor, student, Collections.singletonList(new ChangeItem("registration", (Object) oldRegistration, (Object) registration)), "update");
        student.setRegistration(registration);
        return repository.save(student).convert();
    }


    /**
     * Edita a senha de um {@link Student}
     * @param id o identificador do estudante
     * @param password a nova senha do estudante
     * @param actor o usuário que atualizou o estudante
     * @return {@link StudentResponseDTO} o estudante atualizado
     */
    public StudentResponseDTO editPassword(Long id, String password, User actor) {
        Student student = repository.findById(id).get();
        String oldPassword = student.getPassword();
        logsService.create(actor, student, Collections.singletonList(new ChangeItem("password", (Object) oldPassword, (Object) password)), "update");
        student.setPassword(password);
        return repository.save(student).convert();
    }

    /**
     * Edita a senha de um estudante específico.
     * @param student o estudante a ser editado
     * @param password Nova senha.
     * @return Um booleano indicando se a edição foi bem sucedida
     * @author Gustavo Stinghen
     * @since 19/03/2025
     */
    public boolean editPassword(Student student, String password) {

        try {
            String oldPassword = student.getPassword();
            student.setPassword(password);
            repository.save(student);
            logsService.create( student, student, Collections.singletonList(new ChangeItem("password", (Object) oldPassword, (Object) password)), "update" );
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * Edita a imagem de perfil de um {@link Student}
     * @param id o identificador do estudante
     * @param image a nova imagem do estudante
     * @param actor o usuário que atualizou o estudante
     * @return {@link StudentResponseDTO} o estudante atualizado
     */
    public StudentResponseDTO editImage(Long id, String image, User actor) {
        Student student = repository.findById(id).get();
        String oldImage = student.getImage();
        student.setImage(image);
        logsService.create(actor, student, Collections.singletonList(new ChangeItem("image", (Object) oldImage, (Object) image)), "update");
        return repository.save(student).convert();
    }


    /**
     * Busca todos os {@link Student} com paginação
     * @param pageable as configurações de paginação
     * @throws NaoEncontradoException se nenhum estudante for encontrado
     * @return {@link Page<StudentResponseDTO>} a página contendo os estudantes encontrados
     */
    public Page<StudentResponseDTO> findStudents(Pageable pageable) {
        try {
            return repository.findAll(pageable).map(Student::convert);
        } catch (Exception e) {
            throw new NaoEncontradoException("Alunos nao encontrados");
        }
    }


    /**
     * Busca estudantes de uma determinada {@link Classe} com paginação
     *
     * @param classe a classe desejada
     * @param pageable as configurações de paginação
     * @return {@link Page<StudentResponseDTO>} a página contendo os estudantes encontrados
     * @throws NaoEncontradoException se nenhum estudante for encontrado na classe
     */
    public Page<StudentResponseDTO> findStudentsClass(Classe classe, Pageable pageable) {
        try {
            return repository.findAllByClasses(classe, pageable).map(Student::convert);
        } catch (Exception e) {
            throw new NaoEncontradoException("Alunos nao encontrados");
        }
    }

    /**
     * Busca um {@link Student} pelo email
     * @param email o email do estudante
     * @return {@link Student} o estudante encontrado
     * Utilizado na autenticação
     * @author Gustavo Stinghen
     * @since 19/03/2025
     */
    public Student findObjectStudent ( String email) {
        try {
            return repository.findByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Busca um {@link Student} pelo ID.
     *
     * @param id o identificador do estudante
     * @return {@link StudentResponseDTO} o estudante encontrado
     * @throws NaoEncontradoException se o estudante não for encontrado
     */
    public StudentResponseDTO findId(Long id) {
        try {
            return repository.findById(id).get().convert();
        } catch (Exception e) {
            throw new NaoEncontradoException("Aluno nao encontrado");
        }
    }


    /**
     * Deleta um {@link Student}
     * @param id o identificador do estudante
     * @param actor o usuário que deletou o estudante
     * @throws NaoEncontradoException se o estudante não for encontrado
     */
    public void delete(Long id, User actor) {
        try {
            Student student = repository.findById(id).get();
            repository.deleteById(id);
            logsService.create( actor, student, "delete" );
        } catch (Exception e) {
            throw new NaoEncontradoException("Aluno nao deletado");
        }
    }


    /**
     * Adiciona um {@link Student} a uma {@link Classe}
     * @param student o estudante
     * @param classe a classe a ser adicionada
     * @param actor o usuário que adicionou o estudante
     * @return {@link StudentResponseDTO} o estudante atualizado
     * @throws NaoEncontradoException se a classe não for encontrada
     */
    public StudentResponseDTO addStudentClass(Student student, Classe classe, User actor) {
        if (student.addClasse(classe)) {
            logsService.create( actor, student, Collections.singletonList( new AddItem("classes", (Object) classe ) ), "add" );
            classeService.addStudentToClasse(classe, student, actor);
            return repository.save(student).convert();
        } else {
            throw new NaoEncontradoException("Classe nao encontrada");
        }
    }


    /**
     * Remove um {@link Student} de uma {@link Classe}
     * @param student o estudante
     * @param classe a classe a ser removida
     * @param actor o usuário que removeu o estudante
     * @return {@link StudentResponseDTO} o estudante atualizado
     * @throws NaoEncontradoException se a classe não for encontrada
     */
    public StudentResponseDTO removeStudentClass(Student student, Classe classe, User actor) {
        if (student.removeClasse(classe)) {
            logsService.create( actor, student, Collections.singletonList( new AddItem("classes", (Object) classe ) ), "remove" );
            classeService.removeStudentFromClasse(classe, student, actor);
            return repository.save(student).convert();
        } else {
            throw new NaoEncontradoException("Classe nao encontrada");
        }
    }


    /**
     * Adiciona uma {@link Notification} a um {@link Student}
     * @param id o identificador do estudante
     * @param notification a notificação a ser adicionada
     * @return {@link StudentResponseDTO} o estudante atualizado
     */
    public StudentResponseDTO addNotification(Long id, Notification notification) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Aluno não encontrado"));
        if (!student.addNotification(notification)) {
            throw new NaoEncontradoException("Notificação nao encontrada");
        }
        logsService.create( null, student, Collections.singletonList( new AddItem("notifications", (Object) notification ) ), "add" );
        return repository.save(student).convert();
    }

    /**
     * Remove uma {@link Notification} de um {@link Student}
     * @param id o identificador do estudante
     * @param notification a notificação a ser removida
     * @return {@link StudentResponseDTO} o estudante atualizado
     */
    public StudentResponseDTO removeNotification(Long id, Notification notification) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Aluno não encontrado"));
        if (!student.removeNotification(notification)) {
            throw new NaoEncontradoException("Notificação nao encontrada");
        }
        logsService.create( null, student, Collections.singletonList( new AddItem("notifications", (Object) notification ) ), "remove" );
        return repository.save(student).convert();
    }

    /**
     * Busca um {@link Student} pelo email.
     *
     * @param email o email do estudante
     * @return {@link StudentResponseDTO} o estudante encontrado
     * @throws NaoEncontradoException se o estudante não for encontrado
     */
    public StudentResponseDTO findByEmail(String email) {
        try {
            return repository.findByEmail(email).convert();
        } catch (Exception e) {
            throw new NaoEncontradoException("Aluno nao encontrado");
        }
    }

    /**
     * Promove um {@link Student} para representante da turma.
     *
     * @param id o identificador do estudante
     * @return {@link StudentResponseDTO} o estudante atualizado com o novo papel
     * @throws NaoEncontradoException se o estudante não for encontrado
     */
    public StudentResponseDTO promoteToRepresentative(Long id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Aluno não encontrado"));

        student.setIsRepresentative(true);

        return repository.save(student).convert();
    }

    /**
     * Demite um {@link Student} como representante da turma.
     *
     * @param id o identificador do estudante
     * @return {@link StudentResponseDTO} o estudante atualizado com o novo papel
     * @throws NaoEncontradoException se o estudante nao for encontrado
     */
    public StudentResponseDTO removeToRepresentative(Long id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Aluno não encontrado"));

        student.setIsRepresentative(false);

        return repository.save(student).convert();
    }


    /**
     * Filtra os estudantes por turma.
     *
     * @param classId o identificador da turma
     * @param pageable as configurações de paginação
     * @return {@link Page<StudentResponseDTO>} os estudantes da turma especificada
     * @throws NaoEncontradoException se nenhum estudante for encontrado
     */
    public Page<StudentResponseDTO> filterByClass(Long classId, Pageable pageable) {
        Page<Student> students = repository.findByClasses_Id(classId, pageable);

        if (students.isEmpty()) {
            throw new NaoEncontradoException("Nenhum aluno encontrado para esta turma.");
        }

        return students.map(Student::convert);
    }

    /**
     * Filtra os estudantes por turno.
     *
     * @param shift o turno desejado (ex: "Matutino", "Vespertino", "Noturno")
     * @param pageable as configurações de paginação
     * @return {@link Page<StudentResponseDTO>} os estudantes do turno especificado
     * @throws NaoEncontradoException se nenhum estudante for encontrado
     */
    public Page<StudentResponseDTO> filterByShift(String shift, Pageable pageable) {
        Page<Student> students = repository.findByShift(shift, pageable);

        if (students.isEmpty()) {
            throw new NaoEncontradoException("Nenhum aluno encontrado para este turno.");
        }

        return students.map(Student::convert);
    }

    /**
     * Realiza uma pesquisa inteligente baseada em múltiplos critérios.
     *
     * @param searchTerm o termo de pesquisa (nome, email, matrícula, etc.)
     * @param pageable as configurações de paginação
     * @return {@link Page<StudentResponseDTO>} os estudantes que correspondem ao critério de pesquisa
     * @throws NaoEncontradoException se nenhum estudante for encontrado
     */
    public Page<StudentResponseDTO> intelligentSearch(String searchTerm, Pageable pageable) {
        Page<Student> students = repository.searchByMultipleFields(searchTerm, pageable);

        if (students.isEmpty()) {
            throw new NaoEncontradoException("Nenhum aluno encontrado para o critério de pesquisa fornecido.");
        }

        return students.map(Student::convert);
    }


}

