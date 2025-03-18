package conselho.estudante.com.projetoconselho.SERVICES.USERS;


import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS.StudentRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.StudentResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Classe;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Notification;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Student;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS.NaoEncontradoException;
import conselho.estudante.com.projetoconselho.REPOSITORIES.USERS.StudentRepository;
import lombok.AllArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Date;




/**
 * Classe de serviços para a entidade Student
 * Responsável por operações de CRUD e manipulação de dados do aluno
 * Classe de servicos da entidade Student
 * @author Camilly Chelest
 * @since 12/03/2025
 */


@Service
@AllArgsConstructor
public class StudentService {


    private StudentRepository repository;


    /**
     * Cria um novo {@link Student}
     * @param studentRequestDTO os dados do estudante a ser criado
     * @return {@link StudentResponseDTO} o estudante criado
     * @throws DadosDuplicadosException se o email ou matrícula já estiverem cadastrados
     */
    public StudentResponseDTO create(StudentRequestDTO studentRequestDTO) {
        Student student = studentRequestDTO.convert();
        Date data = new Date();
        student.setCreatedAt(data);
        if (repository.existsByEmail(student.getEmail())) {
            throw new DadosDuplicadosException("Email ja cadastrado");
        } else if (repository.existsByRegistration(student.getRegistration())) {
            throw new DadosDuplicadosException("Matricula ja cadastrada");
        }
        return repository.save(student).convert();
    }


    /**
     * Atualiza um {@link Student} existente
     * @param id o identificador do estudante
     * @param studentRequestDTO os novos dados do estudante
     * @return {@link StudentResponseDTO} o estudante atualizado
     * @throws NaoEncontradoException se o estudante não for encontrado
     */
    public StudentResponseDTO update(Long id, StudentRequestDTO studentRequestDTO) {
        Student student = studentRequestDTO.convert();
        if (repository.existsById(id)) {
            student.setId(id);
            if (repository.existsByEmail(student.getEmail())) {
                throw new DadosDuplicadosException("Email ja cadastrado");
            } else if (repository.existsByRegistration(student.getRegistration())) {
                throw new DadosDuplicadosException("Matricula ja cadastrada");
            }
            return repository.save(student).convert();
        }
        throw new NaoEncontradoException("Aluno nao encontrado");
    }


    /**
     * Edita o nome de um {@link Student}
     * @param id o identificador do estudante
     * @param name o novo nome do estudante
     * @return {@link StudentResponseDTO} o estudante atualizado
     */
    public StudentResponseDTO editName(Long id, String name) {
        Student student = repository.findById(id).get();
        student.setName(name);
        return repository.save(student).convert();
    }


    /**
     * Edita o email de um {@link Student}
     * @param id o identificador do estudante
     * @param email o novo email do estudante
     * @return {@link StudentResponseDTO} o estudante atualizado
     */
    public StudentResponseDTO editEmail(Long id, String email) {
        Student student = repository.findById(id).get();
        student.setEmail(email);
        return repository.save(student).convert();
    }


    /**
     * Edita a matrícula de um {@link Student}
     * @param id o identificador do estudante
     * @param registration a nova matrícula do estudante
     * @return {@link StudentResponseDTO} o estudante atualizado
     */
    public StudentResponseDTO editRegistration(Long id, Long registration) {
        Student student = repository.findById(id).get();
        student.setRegistration(registration);
        return repository.save(student).convert();
    }


    /**
     * Edita a senha de um {@link Student}
     * @param id o identificador do estudante
     * @param password a nova senha do estudante
     * @return {@link StudentResponseDTO} o estudante atualizado
     */
    public StudentResponseDTO editPassword(Long id, String password) {
        Student student = repository.findById(id).get();
        student.setPassword(password);
        return repository.save(student).convert();
    }


    /**
     * Edita a imagem de perfil de um {@link Student}
     * @param id o identificador do estudante
     * @param image a nova imagem do estudante
     * @return {@link StudentResponseDTO} o estudante atualizado
     */
    public StudentResponseDTO editImage(Long id, String image) {
        Student student = repository.findById(id).get();
        student.setImage(image);
        return repository.save(student).convert();
    }


    /**
     * Busca todos os {@link Student} com paginação
     * @param pageable as configurações de paginação
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
     * Busca as classes de um determinado {@link Student} com paginação.
     *
     * @param student o estudante cuja lista de classes será recuperada
     * @param pageable as configurações de paginação
     * @return {@link Page<StudentResponseDTO>} a página contendo os estudantes encontrados na classe
     * @throws NaoEncontradoException se o estudante não estiver matriculado em nenhuma classe
     */
    /*public Page<StudentResponseDTO> findClassStudents(Student student, Pageable pageable) {
        try{
            return student.getClasses();
        } catch (Exception e) {
            throw new NaoEncontradoException("Classes nao encontrados");
        }
    }*/


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
     * @throws NaoEncontradoException se o estudante não for encontrado
     */
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NaoEncontradoException("Aluno nao deletado");
        }
    }


    /**
     * Adiciona um {@link Student} a uma {@link Classe}
     * @param student o estudante
     * @param classe a classe a ser adicionada
     * @return {@link StudentResponseDTO} o estudante atualizado
     * @throws NaoEncontradoException se a classe não for encontrada
     */
    public StudentResponseDTO addStudentClass(Student student, Classe classe) {
        if (student.addClasse(classe)) {
            return repository.save(student).convert();
        } else {
            throw new NaoEncontradoException("Classe nao encontrada");
        }
    }


    /**
     * Remove um {@link Student} de uma {@link Classe}
     * @param student o estudante
     * @param classe a classe a ser removida
     * @return {@link StudentResponseDTO} o estudante atualizado
     * @throws NaoEncontradoException se a classe não for encontrada
     */
    public StudentResponseDTO removeStudentClass(Student student, Classe classe) {
        if (student.removeClasse(classe)) {
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
    /*public StudentResponseDTO addNotification(Long id, Notification notification) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Aluno não encontrado"));
        student.addNotification(notification);
        return repository.save(student).convert();
    }*/


    /**
     * Remove uma {@link Notification} de um {@link Student}
     * @param id o identificador do estudante
     * @param notification a notificação a ser removida
     * @return {@link StudentResponseDTO} o estudante atualizado
     */
    /*public StudentResponseDTO removeNotification(Long id, Notification notification) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Aluno não encontrado"));
        student.removeNotification(notification);
        return repository.save(student).convert();
    }*/


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






}

