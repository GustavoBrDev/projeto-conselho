package conselho.estudante.com.projetoconselho.services.users;


import conselho.estudante.com.projetoconselho.models.dto.request.users.SupervisorRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.users.SupervisorResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Course;
import conselho.estudante.com.projetoconselho.models.entity.administration.Notification;
import conselho.estudante.com.projetoconselho.models.entity.logs.AddItem;
import conselho.estudante.com.projetoconselho.models.entity.logs.ChangeItem;
import conselho.estudante.com.projetoconselho.models.entity.logs.EditableItem;
import conselho.estudante.com.projetoconselho.models.entity.users.Supervisor;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.models.exceptions.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.repositories.users.SupervisorRepository;
import conselho.estudante.com.projetoconselho.services.administration.CourseService;
import conselho.estudante.com.projetoconselho.services.EmailService;
import conselho.estudante.com.projetoconselho.services.logs.UserLogsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe de serviços da entidade Supervisor
 * @author Camilly Chelest
 * @since 20/03/2025
 *
 * Atualizado em 20/03/2025
 * Conexão com o UserLogsService para gerar logs
 * @author Gustavo Stinghen
 * @see UserLogsService
 */

@Service
@AllArgsConstructor
public class SupervisorService {


    private SupervisorRepository repository;
    private UserLogsService logsService;
    private EmailService emailService;
    private CourseService courseService;

    private static final int passwordLength = 8;


    /**
     * Cria um supervisor
     * @param supervisorRequestDTO supervisor a ser criado em formato de {@link SupervisorRequestDTO}
     * @return supervisor criado em formato de {@link SupervisorResponseDTO}
     *
     * Atualizado em 20/03/2023
     * Gera uma senha aleatória e envia um email de boas vindas para o supervisor
     * @author Gustavo Stinghen
     * @param actor o usuário que criou o supervisor
     */
    public SupervisorResponseDTO create(SupervisorRequestDTO supervisorRequestDTO, User actor) {
        Supervisor supervisor = supervisorRequestDTO.convert();
        if (repository.existsByEmail(supervisor.getEmail())) {
            throw new DadosDuplicadosException("Email já cadastrado");
        } else if (repository.existsByRegister(supervisor.getRegister())) {
            throw new DadosDuplicadosException("Cadastro já cadastrado");
        }

        supervisor.setPassword(generateRandomPassword());
        logsService.create(actor, supervisor, "create");
        emailService.sendWelcomeEmail(supervisor.getEmail(), supervisor.getPassword());
        return repository.save(supervisor).convert();
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
     * Atualiza os dados do supervisor
     * @param id identificador do supervisor a ser atualizado
     * @param supervisorRequestDTO supervisor com os dados atualizados
     * @param actor o usuário que atualizou o supervisor
     * @return supervisor atualizado em formato de {@link SupervisorResponseDTO}
     */
    public SupervisorResponseDTO update(Long id, SupervisorRequestDTO supervisorRequestDTO, User actor) {
        Supervisor supervisor = supervisorRequestDTO.convert();
        if (repository.existsById(id)) {
            supervisor.setId(id);
            if (repository.existsByEmail(supervisor.getEmail())) {
                throw new DadosDuplicadosException("Email já cadastrado");
            } else if (repository.existsByRegister(supervisor.getRegister())) {
                throw new DadosDuplicadosException("Cadastro já cadastrado");
            }

            logsService.create( actor, supervisor, getChanges(repository.findById(id).get(), supervisor), "update" );
            supervisor.setCreatedAt(repository.findById(id).get().getCreatedAt());
            return repository.save(supervisor).convert();
        }
        throw new NaoEncontradoException("Supervisor não encontrado");
    }

    public List<EditableItem> getChanges ( Supervisor oldSupervisor, Supervisor newSupervisor) {

        List<EditableItem> changes = new ArrayList<>();

        if (! oldSupervisor.getName().equals(newSupervisor.getName())) {
            changes.add(new ChangeItem("name", (Object) oldSupervisor.getName(), (Object) newSupervisor.getName()));
        }

        if (! oldSupervisor.getEmail().equals(newSupervisor.getEmail())) {
            changes.add(new ChangeItem("email", (Object) oldSupervisor.getEmail(), (Object) newSupervisor.getEmail()));
        }

        if (! oldSupervisor.getRegister().equals(newSupervisor.getRegister())) {
            changes.add(new ChangeItem("register", (Object) oldSupervisor.getRegister(), (Object) newSupervisor.getRegister()));
        }

        if (! oldSupervisor.getPassword().equals(newSupervisor.getPassword())) {
            changes.add(new ChangeItem("password", (Object) oldSupervisor.getPassword(), (Object) newSupervisor.getPassword()));
        }

        if ( ! oldSupervisor.getImage().equals( newSupervisor.getImage() ) ) {
            changes.add(new ChangeItem("image", (Object) oldSupervisor.getImage(), (Object) newSupervisor.getImage()));
        }

        return changes;
    }


    /**
     * Edita o nome de um {@link Supervisor}.
     * @param id o identificador do supervisor
     * @param name o novo nome do supervisor
     * @param actor o usuário que atualizou o supervisor
     * @return {@link SupervisorResponseDTO} o supervisor atualizado
     */
    public SupervisorResponseDTO editName(Long id, String name, User actor) {
        Supervisor supervisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));
        String oldName = supervisor.getName();
        supervisor.setName(name);
        logsService.create(actor, supervisor, Collections.singletonList(new ChangeItem("name", (Object) oldName, (Object) name)), "update");
        return repository.save(supervisor).convert();
    }

    /**
     * Edita o email de um {@link Supervisor}.
     * @param id o identificador do supervisor
     * @param email o novo email do supervisor
     * @param actor o usuário que atualizou o supervisor
     * @return {@link SupervisorResponseDTO} o supervisor atualizado
     */
    public SupervisorResponseDTO editEmail(Long id, String email, User actor) {
        Supervisor supervisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));
        String oldEmail = supervisor.getEmail();
        supervisor.setEmail(email);
        logsService.create(actor, supervisor, Collections.singletonList(new ChangeItem("email", (Object) oldEmail, (Object) email)), "update");
        return repository.save(supervisor).convert();
    }

    /**
     * Edita o cadastro de um {@link Supervisor}.
     * @param id o identificador do supervisor
     * @param register o novo cadastro do supervisor
     * @param actor o usuário que atualizou o supervisor
     * @return {@link SupervisorResponseDTO} o supervisor atualizado
     */
    public SupervisorResponseDTO editRegister(Long id, Long register, User actor) {
        Supervisor supervisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));
        Long oldRegister = supervisor.getRegister();
        supervisor.setRegister(register);
        logsService.create(actor, supervisor, Collections.singletonList(new ChangeItem("register", (Object) oldRegister, (Object) register)), "update");
        return repository.save(supervisor).convert();
    }

    /**
     * Edita a senha de um {@link Supervisor}.
     * @param id o identificador do supervisor
     * @param password a nova senha do supervisor
     * @param actor o usuário que atualizou o supervisor
     * @return {@link SupervisorResponseDTO} o supervisor atualizado
     */
    public SupervisorResponseDTO editPassword(Long id, String password, User actor) {
        Supervisor supervisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));
        String oldPassword = supervisor.getPassword();
        supervisor.setPassword(password);
        logsService.create(actor, supervisor, Collections.singletonList(new ChangeItem("password", (Object) oldPassword, (Object) password)), "update");
        return repository.save(supervisor).convert();
    }

    /**
     * Edita a senha de um supervisor
     * @param supervisor Supervisor a ser editado
     * @param password Nova senha.
     * @return Um booleano indicando se a edição foi bem sucedida
     * @author Gustavo Stinghen
     * @since 19/03/2025
     */
    public boolean editPassword(Supervisor supervisor, String password) {

        try {
            supervisor.setPassword(password);
            repository.save(supervisor);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Edita a imagem de perfil de um {@link Supervisor}.
     * @param id o identificador do supervisor
     * @param image a nova imagem do supervisor
     * @param actor o usuário que atualizou o supervisor
     * @return {@link SupervisorResponseDTO} o supervisor atualizado
     */
    public SupervisorResponseDTO editImage(Long id, String image, User actor) {
        Supervisor supervisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));
        String oldImage = supervisor.getImage();
        supervisor.setImage(image);
        logsService.create(actor, supervisor, Collections.singletonList(new ChangeItem("image", (Object) oldImage, (Object) image)), "update");
        return repository.save(supervisor).convert();
    }

    /**
     * Retorna todos os supervisores cadastrados com paginação.
     * @param pageable as configurações de paginação
     * @return {@link Page<SupervisorResponseDTO>} a página contendo os supervisores encontrados
     * @throws NaoEncontradoException se não houver supervisores cadastrados
     */
    public Page<SupervisorResponseDTO> findSupervisors(Pageable pageable) {
        try {
            return repository.findAll(pageable)
                    .map(Supervisor::convert);
        } catch (Exception e) {
            throw new NaoEncontradoException("Supervisores não encontrados");
        }
    }

    /**
     * Retorna as notificações de um {@link Supervisor}.
     * @param id o identificador do supervisor
     * @return {@link List<Notification>} a lista de notificações do supervisor
     * @throws NaoEncontradoException se o supervisor não for encontrado
     */
    /*public List<Notification> getNotifications(Long id) {
        Supervisor supervisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));
        return supervisor.getNotifications();
    }*/




    /**
     * Busca um {@link Supervisor} pelo seu identificador.
     * @param id o identificador do supervisor
     * @return {@link SupervisorResponseDTO} o supervisor encontrado
     * @throws NaoEncontradoException se o supervisor não for encontrado
     */
    public SupervisorResponseDTO findById(Long id) {
        Supervisor supervisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));
        return supervisor.convert();
    }




    /**
     * Busca um {@link Supervisor} pelo email.
     * @param email o email do supervisor
     * @return {@link SupervisorResponseDTO} o supervisor encontrado
     * @throws NaoEncontradoException se o supervisor não for encontrado
     */
    public SupervisorResponseDTO findByEmail(String email) {
        Supervisor supervisor = repository.findByEmail(email);
        if (supervisor != null) {
            return supervisor.convert();
        } else {
            throw new NaoEncontradoException("Supervisor não encontrado");
        }
    }

    /**
     * Busca um {@link Supervisor} pelo email
     * @param email o email do supervisor
     * @return {@link Supervisor} o supervisor encontrado
     * Utilizado na autenticação
     * @author Gustavo Stinghen
     * @since 19/03/2025
     */
    public Supervisor findObjectSupervisor ( String email) {
        try {
            return repository.findByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Busca um {@link Supervisor} pelo email
     * @param id o identificador do supervisor
     * @return {@link Supervisor} o supervisor encontrado
     * Utilizado na autenticação
     * @author Gustavo Stinghen
     * @since 24/03/2025
     */
    public Supervisor findObjectSupervisor ( Long id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Adiciona uma {@link Notification} a um {@link Supervisor}.
     * @param id o identificador do supervisor
     * @param notification a notificação a ser adicionada
     * @return {@link SupervisorResponseDTO} o supervisor atualizado
     * @throws NaoEncontradoException se o supervisor não for encontrado
     */
    public SupervisorResponseDTO addNotification(Long id, Notification notification) {
        Supervisor supervisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));

        if ( ! supervisor.addNotification(notification) ) {
            throw new NaoEncontradoException("Notificação nao encontrada");
        }

        logsService.create( null, supervisor, Collections.singletonList( new AddItem("notifications", (Object) notification ) ), "add" );
        return repository.save(supervisor).convert();
    }

    /**
     * Remove uma {@link Notification} de um {@link Supervisor}.
     * @param id o identificador do supervisor
     * @param notification a notificação a ser removida
     * @return {@link SupervisorResponseDTO} o supervisor atualizado
     * @throws NaoEncontradoException se o supervisor não for encontrado
     */
    public SupervisorResponseDTO removeNotification(Long id, Notification notification) {
        Supervisor supervisor = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));

        if ( ! supervisor.removeNotification(notification) ) {
            throw new NaoEncontradoException("Notificação nao encontrada");
        }

        logsService.create( null, supervisor, Collections.singletonList( new AddItem("notifications", (Object) notification ) ), "remove" );
        return repository.save(supervisor).convert();
    }

    /**
     * Deleta um {@link Supervisor}.
     * @param id o identificador do supervisor
     * @param actor o usuário que deletou o supervisor
     * @throws NaoEncontradoException se o supervisor não for encontrado
     */
    public void delete(Long id, User actor) {
        try {
            Supervisor supervisor = repository.findById(id).get();
            repository.deleteById(id);
            logsService.create( actor, supervisor, "delete" );
        } catch (Exception e) {
            throw new NaoEncontradoException("Supervisor não deletado");
        }
    }


    /**
     * Adiciona um {@link Course} a um {@link Supervisor}.
     * @param supervisorId o identificador do supervisor
     * @param course o curso a ser adicionado
     * @param actor o usuário que adicionou o curso
     * @return {@link SupervisorResponseDTO} o supervisor atualizado
     * @throws NaoEncontradoException se o supervisor não for encontrado
     */
    public SupervisorResponseDTO addCourse(Long supervisorId, Course course, User actor) {
        Supervisor supervisor = repository.findById(supervisorId)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));

        supervisor.addCourse(course);
        courseService.editSupervisor(course.getId(), supervisorId, actor);
        logsService.create( actor, supervisor, Collections.singletonList( new AddItem("courses", (Object) course ) ), "add" );

        return repository.save(supervisor).convert();
    }


    /**
     * Remove um {@link Course} de um {@link Supervisor}.
     * @param supervisorId o identificador do supervisor
     * @param course o curso a ser removido
     * @param actor o usuário que removeu o curso
     * @return {@link SupervisorResponseDTO} o supervisor atualizado
     * @throws NaoEncontradoException se o supervisor não for encontrado ou não estiver associado ao curso
     */
    public SupervisorResponseDTO removeCourse(Long supervisorId, Course course, User actor) {
        Supervisor supervisor = repository.findById(supervisorId)
                .orElseThrow(() -> new NaoEncontradoException("Supervisor não encontrado"));

        supervisor.removeCourse(course);
        logsService.create( actor, supervisor, Collections.singletonList( new AddItem("courses", (Object) course ) ), "remove" );

        return repository.save(supervisor).convert();
    }

    /**
     * Filtra os supervisores por curso.
     *
     * @param courseId o identificador do curso
     * @param pageable as configurações de paginação
     * @return {@link Page<SupervisorResponseDTO>} os supervisores do curso especificado
     * @throws NaoEncontradoException se nenhum supervisor for encontrado
     */
    public Page<SupervisorResponseDTO> filterByCourse(Long courseId, Pageable pageable) {
        Page<Supervisor> supervisors = repository.findByCourses_Id(courseId, pageable);

        if (supervisors.isEmpty()) {
            throw new NaoEncontradoException("Nenhum supervisor encontrado para este curso.");
        }

        return supervisors.map(Supervisor::convert);
    }

    /**
     * Filtra os supervisores por turma.
     *
     * @param classId o identificador da turma
     * @param pageable as configurações de paginação
     * @return {@link Page<SupervisorResponseDTO>} os supervisores da turma especificada
     * @throws NaoEncontradoException se nenhum supervisor for encontrado
     */
    public Page<SupervisorResponseDTO> filterByClass(Long classId, Pageable pageable) {
        /*Page<Supervisor> supervisors = repository.findByClasses_Id(classId, pageable);

        if (supervisors.isEmpty()) {
            throw new NaoEncontradoException("Nenhum supervisor encontrado para esta turma.");
        }

        return supervisors.map(Supervisor::convert);*/
        return null;
    }

    /**
     * Realiza uma pesquisa inteligente baseada em múltiplos critérios.
     *
     * @param searchTerm o termo de pesquisa (nome, email, matrícula, etc.)
     * @param pageable as configurações de paginação
     * @return {@link Page<SupervisorResponseDTO>} os supervisores que correspondem ao critério de pesquisa
     * @throws NaoEncontradoException se nenhum supervisor for encontrado
     */
    public Page<SupervisorResponseDTO> intelligentSearch(String searchTerm, Pageable pageable) {
        Page<Supervisor> supervisors = repository.searchByMultipleFields(searchTerm, pageable);

        if (supervisors.isEmpty()) {
            throw new NaoEncontradoException("Nenhum supervisor encontrado para o critério de pesquisa fornecido.");
        }

        return supervisors.map(Supervisor::convert);
    }
}

