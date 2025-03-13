package SERVICES;

import MODELS.DTO.REQUEST.StudentRequestDTO;
import MODELS.DTO.RESPONSE.StudentResponseDTO;
import MODELS.ENTITY.ADMINISTRATION.Classe;
import MODELS.ENTITY.USERS.Student;
import MODELS.EXCEPTIONS.DadosDuplicadosException;
import MODELS.EXCEPTIONS.NaoEncontradoException;
import REPOSITORIES.USERS.StudentRepository;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Classe de servicos da entidade Student
 * @author Camilly Chelest
 * @since 12/03/2025
 */

@Service
@AllArgsConstructor
public class StudentService {

    private StudentRepository repository;

    /**
     * Cria um aluno
     * @param studentRequestDTO aluno a ser criado em formato de {@link StudentRequestDTO}
     * @return aluno criado em formato de {@link StudentResponseDTO}
     */
    public StudentResponseDTO create(StudentRequestDTO studentRequestDTO) {
        Student student = studentRequestDTO.convert();
        if (repository.existsByEmail(student.getEmail())) {
            throw new DadosDuplicadosException("Email ja cadastrado");
        } else if (repository.existsByRegistration(student.getRegistration())) {
            throw new DadosDuplicadosException("Matricula ja cadastrada");
        }
        return repository.save(student).convert();
    }

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

    public StudentResponseDTO editName(Long id, String name) {
        Student student = repository.findById(id).get();
        student.setName(name);
        return repository.save(student).convert();
    }

    public StudentResponseDTO editEmail(Long id, String email) {
        Student student = repository.findById(id).get();
        student.setEmail(email);
        return repository.save(student).convert();
    }

    public StudentResponseDTO editRegistration(Long id, String registration) {
        Student student = repository.findById(id).get();
        student.setRegistration(registration);
        return repository.save(student).convert();
    }

    public StudentResponseDTO editPassword(Long id, String password) {
        Student student = repository.findById(id).get();
        student.setPassword(password);
        return repository.save(student).convert();
    }

    public StudentResponseDTO editImage(Long id, String image) {
        Student student = repository.findById(id).get();
        student.setImage(image);
        return repository.save(student).convert();
    }

    public Page<StudentResponseDTO> findStudents(Pageable pageable) {
        try {
            return repository.findAll(pageable).map(StudentResponseDTO::convert);
        } catch (Exception e) {
            throw new NaoEncontradoException("Alunos nao encontrados");
        }
    }

    public Page<StudentResponseDTO> findStudentsClass(Classe classe, Pageable pageable) {
        try {
            return repository.findAllByClasses(classe, pageable).map(StudentResponseDTO::convert);
        } catch (Exception e) {
            throw new NaoEncontradoException("Alunos nao encontrados");
        }
    }

    public Page<StudentResponseDTO> findClassStudents(Student student, Pageable pageable) {
        try{
            return student.getClasses();
        } catch (Exception e) {
            throw new NaoEncontradoException("Classes nao encontrados");
        }
    }

    public StudentResponseDTO findId(Long id) {
        try {
            return repository.findById(id).convert();
        } catch (Exception e) {
            throw new NaoEncontradoException("Aluno nao encontrado");
        }
    }




    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NaoEncontradoException("Aluno nao deletado");
        }
    }


    public StudentResponseDTO addStudentClass(Student student, Classe classe) {
        if (student.addClasse(classe)) {
            return repository.save(student).convert();
        } else {
            throw new NaoEncontradoException("Classe nao encontrada");
        }
    }

    public StudentResponseDTO removeStudentClass(Student student, Classe classe) {
        if (student.removeClasse(classe)) {
            return repository.save(student).convert();
        } else {
            throw new NaoEncontradoException("Classe nao encontrada");
        }
    }






}
