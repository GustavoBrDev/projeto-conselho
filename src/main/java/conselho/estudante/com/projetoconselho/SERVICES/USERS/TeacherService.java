package conselho.estudante.com.projetoconselho.SERVICES.USERS;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.USERS.TeacherResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.REQUEST.USERS.TeacherRequestDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Course;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Shift;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.ADMINISTRATION.Subject;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.Teacher;
import conselho.estudante.com.projetoconselho.REPOSITORIES.USERS.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Classe para o servico de Teacher
 * autor Alex Zastrow
 */

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    /*
     * Metodo para converter um TeacherRequestDTO para um Teacher
     */
    public Teacher toEntity(TeacherRequestDTO dto) {
        Teacher teacher = new Teacher();
        teacher.setName(dto.getName());
        teacher.setUsername(dto.getUsername());
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
        TeacherResponseDTO dto = new TeacherResponseDTO();
        dto.setId(teacher.getId());
        dto.setName(teacher.getName());
        dto.setUsername(teacher.getUsername());
        dto.setEmail(teacher.getEmail());
        dto.setImage(teacher.getImage());
        dto.setRegister(teacher.getRegister());

        dto.setCourses(teacher.getCourses().stream().map(Course::getName).collect(Collectors.toList()));
        dto.setSubjects(teacher.getSubjects().stream().map(Subject::getName).collect(Collectors.toList()));
        dto.setShifts(teacher.getShifts().stream().map(Shift::getName).collect(Collectors.toList()));
        return dto;
    }

    /*
     * Metodo para criar um Teacher
     */
    public Teacher criarTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
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
}