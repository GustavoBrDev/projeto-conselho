package conselho.estudante.com.projetoconselho.controller.administration;

import conselho.estudante.com.projetoconselho.models.dto.request.ADMINISTRATION.CourseRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.ADMINISTRATION.CourseResponseDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.ADMINISTRATION.SubjectResponseDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.USERS.TeacherResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Classe;
import conselho.estudante.com.projetoconselho.models.entity.administration.Course;
import conselho.estudante.com.projetoconselho.models.entity.administration.Shift;
import conselho.estudante.com.projetoconselho.models.entity.administration.Subject;
import conselho.estudante.com.projetoconselho.models.entity.users.Teacher;
import conselho.estudante.com.projetoconselho.models.entity.users.User;
import conselho.estudante.com.projetoconselho.services.administration.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Classe de controller da entidade Course
 *
 * @author joana voigt
 * @since 31/03/2025
 *
 * @see CourseService
 *
 */
@RestController
@RequestMapping("/administration/courses")
@AllArgsConstructor
@Tag(name = "Cursos", description = "Recurso para gerenciamento de cursos")
public class CourseController {
    private CourseService service;

    @Operation(summary = "Cria um curso", description = "Cria um curso e retorna com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Curso criado com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
            examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao criar curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PostMapping
    public ResponseEntity<CourseResponseDTO> postCourse(
            @Parameter (description = "Curso a ser criado", content =
            @Content(schema = @Schema(implementation = CourseResponseDTO.class)),
            required = true, example = "{" +
                    "\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")
            @RequestBody @Valid CourseRequestDTO courseRequestDTO,
            @RequestParam @Parameter(description = "Usuário que criou o curso", required = true) User actor) {

        try {
            return new ResponseEntity<>(service.create(courseRequestDTO, actor), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Atualiza um curso", description = "Atualiza um curso existente e retorna o curso atualizado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> putCourse(
            @Parameter (description = "Dados do curso a ser atualizado", content =
            @Content(schema = @Schema(implementation = CourseRequestDTO.class)),
                    required = true, example = "{" +
                    "\"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")
            @RequestBody @Valid CourseRequestDTO courseRequestDTO,
            @Parameter(description = "ID do curso a ser atualizado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Usuário que atualizou o curso", required = true) User actor ) {

        try {
            return new ResponseEntity<>(service.update(id, courseRequestDTO, actor), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita o nome de um curso", description = "Edita o nome de um curso existente e retorna o curso atualizado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editName/{id}")
    public ResponseEntity<String> editName(
            @Parameter (description = "ID do curso a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Novo nome do curso", required = true) String name,
            @RequestParam @Parameter(description = "Usuário que editou o curso", required = true) User actor) {

        try {
            service.editName(id, name, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita a identidade visual de um curso", description = "Edita a identidade visual de um curso existente e retorna o curso atualizado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editVisualIdentity/{id}")
    public ResponseEntity<String> editVisualIdentity(
            @Parameter (description = "ID do curso a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Nova identidade visual do curso", required = true) String visualIdentity,
            @RequestParam @Parameter(description = "Usuário que editou o curso", required = true) User actor) {

        try {
            service.editVisualIdentity(id, visualIdentity, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita a carga horaria de um curso", description = "Edita a carga horaria de um curso existente e retorna o curso atualizado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editWorkLoad/{id}")
    public ResponseEntity<String> editWorkLoad(
            @Parameter (description = "ID do curso a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Nova carga horaria do curso", required = true) Integer workLoad,
            @RequestParam @Parameter(description = "Usuário que editou o curso", required = true) User actor) {

        try {
            service.editWorkLoad(id, workLoad, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita o nivel de um curso", description = "Edita o nivel de um curso existente e retorna o curso atualizado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editLevel/{id}")
    public ResponseEntity<String> editLevel(
            @Parameter (description = "ID do curso a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Nova nivel do curso", required = true) String level,
            @RequestParam @Parameter(description = "Usuário que editou o curso", required = true) User actor) {

        try {
            service.editLevel(id, level, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita o supervisor de um curso", description = "Edita o supervisor de um curso existente e retorna o curso atualizado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editSupervisor/{id}")
    public ResponseEntity<String> editSupervisor(
            @Parameter (description = "ID do curso a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Novo supervisor do curso", required = true) Long supervisorId,
            @RequestParam @Parameter(description = "Usuário que editou o curso", required = true) User actor) {

        try {
            service.editSupervisor(id, supervisorId, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita o turno de um curso", description = "Edita o turno de um curso existente e retorna o curso atualizado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editShift/{id}")
    public ResponseEntity<String> editShift(
            @Parameter (description = "ID do curso a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Novo turno do curso", required = true) Shift shift,
            @RequestParam @Parameter(description = "Usuário que editou o curso", required = true) User actor) {

        try {
            service.editShift(id, shift, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca todos os cursos", description = "Busca todos os cursos existentes e retorna o curso atualizado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Curso encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao encontrar curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/allCourses")
    public ResponseEntity<Page<CourseResponseDTO>> getAllCourses(
            @Parameter (description = "Busca todos os cursos", content =
        @Content(schema = @Schema(implementation = CourseResponseDTO.class)),
                example = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC ) Pageable pageable) {

        try {
            return new ResponseEntity<>(service.findCourses(pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca todos os professores de um curso", description = "Busca todos os professores de um curso e retorna o curso atualizado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Curso encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao encontrar curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/teachers/{id}")
    public ResponseEntity<Page<TeacherResponseDTO>> listTeacherByCourse(
            @Parameter (description = "ID do curso", required = true, example = "1") @PathVariable Long courseId,
            @Parameter (description = "Pagina para listar professores", required = true)
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        Page<Teacher> teachers = service.findTeacherByCourse(courseId, pageable);
        Page<TeacherResponseDTO> teacherResponseDTOs = teachers.map(Teacher::toDTO);
        return ResponseEntity.ok(teacherResponseDTOs);
    }

    @Operation(summary = "Busca todas as materias de um curso", description = "Busca todas as materias de um curso e retorna o curso atualizado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Curso encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao encontrar curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/subjects/{id}")
    public ResponseEntity<Page<SubjectResponseDTO>> listSubjectByCourse(
            @Parameter (description = "ID do curso", required = true, example = "1") @PathVariable Long courseId,
            @Parameter (description = "Pagina para listar máterias", required = true)
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        Page<Subject> subjects = service.findSubjectByCourse(courseId, pageable);
        Page<SubjectResponseDTO> subjectResponseDTOS = subjects.map(Subject::toDTO);
        return ResponseEntity.ok(subjectResponseDTOS);
    }

    @Operation(summary = "Adiciona um professor a um curso", description = "Adiciona um professor a um curso e retorna o curso atualizado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Professor adicionado com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar professor")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/addTeacher/{course}/{teacher}")
    public ResponseEntity<String> addTeacherToCourse(
            @Parameter(description = "Curso", required = true) @PathVariable Course course,
            @Parameter(description = "Professor", required = true) @PathVariable Teacher teacher,
            @Parameter(description = "Usuário que adicionou o professor", required = true) @RequestParam User actor) {

        try{
            service.addTeacherToCourse(course, teacher, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Adiciona uma materia a um curso", description = "Adiciona uma materia a um curso e retorna o curso atualizado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Materia adicionada com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar materia")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/addSubject/{course}/{subject}")
    public ResponseEntity<String> addSubjectToCourse(
            @Parameter(description = "Curso", required = true) @PathVariable Course course,
            @Parameter(description = "Materia", required = true) @PathVariable Subject subject,
            @Parameter(description = "Usuário que adicionou o professor", required = true) @RequestParam User actor) {

        try{
            service.addSubjectToCourse(course, subject, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Adiciona uma classe a um curso", description = "Adiciona uma classe a um curso e retorna o curso atualizado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Classe adicionada com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar classe")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/addClass/{course}/{classe}")
    public ResponseEntity<String> addClassToCourse(
            @Parameter(description = "Curso", required = true) @PathVariable Course course,
            @Parameter(description = "Classe", required = true) @PathVariable Classe classe,
            @Parameter(description = "Usuário que adicionou o professor", required = true) @RequestParam User actor) {

        try{
            service.addClassToCourse(course, classe, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Remove um professor de um curso", description = "Remove um professor de um curso e retorna o curso atualizado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Professor removido com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao remover professor")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/removeTeacher/{course}/{teacher}")
    public ResponseEntity<String> removeTeacherFromCourse(
            @Parameter(description = "Curso", required = true) @PathVariable Course course,
            @Parameter(description = "Professor", required = true) @PathVariable Teacher teacher,
            @Parameter(description = "Usuário que adicionou o professor", required = true) @RequestParam User actor) {

        try {
            service.removeTeacherFromCourse(course, teacher, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Remove uma materia de um curso", description = "Remove uma materia de um curso e retorna o curso atualizado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Materia removida com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao remover materia")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/removeSubject/{course}/{subject}")
    public ResponseEntity<String> removeSubjectFromCourse(
            @Parameter(description = "Curso", required = true) @PathVariable Course course,
            @Parameter(description = "Materia", required = true) @PathVariable Subject subject,
            @Parameter(description = "Usuário que adicionou o professor", required = true) @RequestParam User actor) {

        try {
            service.removeSubjectFromCourse(course, subject, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Remove uma classe de um curso", description = "Remove uma classe de um curso e retorna o curso atualizado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Classe removida com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao remover classe")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/removeClass/{course}/{classe}")
    public ResponseEntity<String> removeClassFromCourse(
            @Parameter(description = "Curso", required = true) @PathVariable Course course,
            @Parameter(description = "Classe", required = true) @PathVariable Classe classe,
            @Parameter(description = "Usuário que adicionou o professor", required = true) @RequestParam User actor) {

        try {
            service.removeClassFromCourse(course, classe, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca um curso por ID", description = "Busca um curso por ID e retorna o com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Curso encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao encontrar curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(
            @Parameter(description = "ID do curso", required = true) @PathVariable Long id) {

        try {
            return new ResponseEntity<>(service.findCourseById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Deleta um curso", description = "Deleta um curso e retorna com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Curso deletado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao deletar curso ")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(
            @Parameter(description = "ID do curso", required = true) @PathVariable Long id,
            @Parameter(description = "Usuário que adicionou o professor", required = true) @RequestParam User actor) {

        try {
            service.delete(id, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
