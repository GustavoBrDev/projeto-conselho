package conselho.estudante.com.projetoconselho.SERVICES.USERS;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.TeacherResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS.TeacherRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import conselho.estudante.com.projetoconselho.REPOSITORIES.USERS.TeacherRepository;
import conselho.estudante.com.projetoconselho.SERVICES.LOGS.UserLogsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * Classe para o servico de Teacher
 * autor Alex Zastrow
 */

@Service
@AllArgsConstructor
public class TeacherService {

    private TeacherRepository teacherRepository;
    private UserLogsService logsService;

    /*
     * Metodo para converter um TeacherRequestDTO para um Teacher
     */
    public Teacher toEntity(TeacherRequestDTO dto) {
        Teacher teacher = new Teacher();
        teacher.setName(dto.getName());
        teacher.setEmail(dto.getEmail());
        teacher.setPassword(dto.getPassword());
        teacher.setRegister(dto.getRegister());
        teacher.setImage(dto.getImage());
        return teacher;
    }

    /*
     * Metodo para converter um Teacher para um TeacherResponseDTO
     */
    public TeacherResponseDTO toResponseDTO(Teacher teacher) {
        TeacherResponseDTO dto = TeacherResponseDTO.builder()
                .id(teacher.getId())
                .name(teacher.getName())
                .email(teacher.getEmail())
                .image(teacher.getImage())
                .register(teacher.getRegister())
                .build();

        return dto;
    }

    /*
     * Metodo para criar um Teacher
     */
    public Teacher criarTeacher(Teacher teacher) {
        teacher.setCreatedAt(new Date());
        System.out.println("Acessou o service");

        Teacher savedTeacher = teacherRepository.save(teacher);
        logsService.create(savedTeacher, teacher, "create");

        return savedTeacher;
    }

    /*
     * Metodo para atualizar um Teacher
     */
    public Teacher atualizarTeacher(Long id, Teacher teacher) {
        teacher.setId(id);
        return teacherRepository.save(teacher);
    }

    /*
     * Metodo para buscar um Teacher por id
     */
    public Teacher buscarPorId(Long id) {
        return teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher not found"));
    }

    /**
     * Metodo para buscar um Teacher por id
     * @param id id do Teacher
     * @return Teacher em formato de {@link Teacher}
     * @author Gustavo Stinghen
     * @since 26/03/2025
     * @see Teacher
     */
    public Teacher getObjectTeacher(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }
}