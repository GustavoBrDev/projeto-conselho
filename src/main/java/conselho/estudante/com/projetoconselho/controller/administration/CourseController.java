package conselho.estudante.com.projetoconselho.controller.administration;

import conselho.estudante.com.projetoconselho.models.dto.request.administration.ClasseRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.request.administration.CourseRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.request.administration.SubjectRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.request.users.TeacherRequestDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.administration.CourseResponseDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.administration.SubjectResponseDTO;
import conselho.estudante.com.projetoconselho.models.dto.response.users.TeacherResponseDTO;
import conselho.estudante.com.projetoconselho.models.entity.administration.Classe;
import conselho.estudante.com.projetoconselho.models.entity.administration.Course;
import conselho.estudante.com.projetoconselho.models.entity.administration.Shift;
import conselho.estudante.com.projetoconselho.models.entity.administration.Subject;
import conselho.estudante.com.projetoconselho.models.entity.users.Admin;
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
            @Content(schema = @Schema(implementation = CourseRequestDTO.class)),
            required = true, example = "{" +
            " \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"workLoad\": \"8\", \"level\": \"1\"}")
            @RequestBody @Valid CourseRequestDTO courseRequestDTO/*,
            @RequestParam @Parameter(description = "Usuário que criou o curso", required = true) User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();
        try {
            return new ResponseEntity<>(service.create(courseRequestDTO, actor), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
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
            @Parameter(description = "ID do curso a ser atualizado", required = true) @PathVariable Long id/*,
            @RequestParam @Parameter(description = "Usuário que atualizou o curso", required = true) User actor*/ ) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();

        try {
            return new ResponseEntity<>(service.update(id, courseRequestDTO, actor), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
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
    public ResponseEntity<CourseResponseDTO> editName(
            @Parameter (description = "ID do curso a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Novo nome do curso", required = true) String name/*,
            @RequestParam @Parameter(description = "Usuário que editou o curso", required = true) User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();

        try {
            return new ResponseEntity<>(service.editName(id, name, actor), HttpStatus.OK);
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
    public ResponseEntity<CourseResponseDTO> editVisualIdentity(
            @Parameter (description = "ID do curso a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Nova identidade visual do curso", required = true) String visualIdentity/*,
            @RequestParam @Parameter(description = "Usuário que editou o curso", required = true) User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();

        try {
            return new ResponseEntity<>(service.editVisualIdentity(id, visualIdentity, actor), HttpStatus.OK);
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
    public ResponseEntity<CourseResponseDTO> editWorkLoad(
            @Parameter (description = "ID do curso a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Nova carga horaria do curso", required = true) Integer workLoad/*,
            @RequestParam @Parameter(description = "Usuário que editou o curso", required = true) User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();

        try {
            return new ResponseEntity<>(service.editWorkLoad(id, workLoad, actor), HttpStatus.OK);
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
    public ResponseEntity<CourseResponseDTO> editLevel(
            @Parameter (description = "ID do curso a ser editado", required = true) @PathVariable Long id,
            @RequestParam @Parameter(description = "Nova nivel do curso", required = true) String level/*,
            @RequestParam @Parameter(description = "Usuário que editou o curso", required = true) User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();

        try {
            return new ResponseEntity<>(service.editLevel(id, level, actor), HttpStatus.OK);
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
    @PatchMapping("/editSupervisor/{id}/{supervisorId}")
    public ResponseEntity<CourseResponseDTO> editSupervisor(
            @Parameter (description = "ID do curso a ser editado", required = true, example = "1") @PathVariable Long id,
            @PathVariable @Parameter(description = "Novo supervisor do curso", required = true, example = "1") Long supervisorId/*,
            @RequestParam @Parameter(description = "Usuário que editou o curso", required = true) User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();

        try {
            return new ResponseEntity<>(service.editSupervisor(id, supervisorId, actor), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edita o turno de um curso", description = "Edita o turno de um curso existente e retorna o curso atualizado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/editShift/{id}/{shiftId}")
    public ResponseEntity<CourseResponseDTO> editShift(
            @Parameter (description = "ID do curso a ser editado", required = true, example = "1") @PathVariable Long id,
            @PathVariable @Parameter(description = "Novo turno do curso", required = true, example = "1") Long shiftId/*,
            @RequestParam @Parameter(description = "Usuário que editou o curso", required = true) User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();

        try {
            return new ResponseEntity<>( service.editShift(id, shiftId, actor), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca todos os cursos", description = "Busca todos os cursos existentes e retorna o curso atualizado com o status HTTP 200" )
    @ApiResponse(responseCode = "200", description = "Curso encontrado com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\",}")))
    @ApiResponse(responseCode = "400", description = "Erro ao encontrar curso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping
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
            @Parameter (description = "ID do curso", required = true, example = "1") @PathVariable Long id,
            @Parameter (description = "Pagina para listar professores", required = true)
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        Page<Teacher> teachers = service.findTeacherByCourse(id, pageable);
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
            @Parameter (description = "ID do curso", required = true, example = "1") @PathVariable Long id,
            @Parameter (description = "Pagina para listar máterias", required = true)
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        Page<Subject> subjects = service.findSubjectByCourse(id, pageable);
        Page<SubjectResponseDTO> subjectResponseDTOS = subjects.map(Subject::toDTO);
        return ResponseEntity.ok(subjectResponseDTOS);
    }

    @Operation(summary = "Adiciona um professor a um curso", description = "Adiciona um professor a um curso e retorna o curso atualizado com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "Professor adicionado com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar professor")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/addTeacher/{id}/{teacherId}")
    public ResponseEntity<String> addTeacherToCourse(
            @Parameter(description = "Curso", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "ID do professor", required = true, example = "1") @PathVariable Long teacherId
           /*@Parameter(description = "Usuário que adicionou o professor", required = true) @RequestParam User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();

        try {
            service.addTeacherToCourse(id, teacherId, actor);
            return new ResponseEntity<>("Professor adicionado com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Adiciona uma materia a um curso", description = "Adiciona uma materia a um curso e retorna o curso atualizado com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "Materia adicionada com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar materia")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/addSubject/{id}/{subjectId}")
    public ResponseEntity<String> addSubjectToCourse(
            @Parameter(description = "Curso", required = true, example = "1") @PathVariable Long id,
           @Parameter(description = "Materia", required = true, example = "1") @PathVariable Long subjectId
           /*@Parameter(description = "Usuário que adicionou o professor", required = true) @RequestParam User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();

        try {
            service.addSubjectToCourse(id, subjectId, actor);
            return new ResponseEntity<>("Materia adicionada com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Adiciona uma classe a um curso", description = "Adiciona uma classe a um curso e retorna o curso atualizado com o status HTTP 200")
    @ApiResponse(responseCode = "200", description = "Classe adicionada com sucesso",
            content = @Content(schema = @Schema(implementation = CourseResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Desenvolvimento de Sistemas\", \"visualIdentity\": \"#FF0000\", \"createdAt\": \"2023-01-01\", \"workLoad\": \"8\", \"level\": \"1\", \"subjects\": [1,2,3], \"teacher\": [1,2,3], \"shift\": \"Manhã\", \"classes\": [1,2,3], \"Supervisor\": \"Joaquim\"}")))
    @ApiResponse(responseCode = "400", description = "Erro ao adicionar classe")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PatchMapping("/addClass/{id}/{classeId}")
    public ResponseEntity<String> addClassToCourse(
            @Parameter(description = "Curso", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "Classe", required = true, example = "1") @PathVariable Long classeId
            /*@Parameter(description = "Usuário que adicionou o professor", required = true) @RequestParam User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();

        try {
            service.addClassToCourse(id, classeId, actor);
            return new ResponseEntity<>("Classe adicionada com sucesso", HttpStatus.OK);
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
    @PatchMapping("/removeTeacher/{id}/{teacherId}")
    public ResponseEntity<String> removeTeacherFromCourse(
            @Parameter(description = "Curso", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "Professor", required = true, example = "1") @PathVariable Long teacherId
           /*@Parameter(description = "Usuário que adicionou o professor", required = true) @RequestParam User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();

        try {
            service.removeTeacherFromCourse(id, teacherId, actor);
            return new ResponseEntity<>("Professor removido com sucesso", HttpStatus.OK);
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
    @PatchMapping("/removeSubject/{id}/{subjectId}")
    public ResponseEntity<String> removeSubjectFromCourse(
            @Parameter(description = "Curso", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "Materia", required = true, example = "1") @PathVariable Long subjectId
           /*@Parameter(description = "Usuário que adicionou o professor", required = true) @RequestParam User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();

        try {
            service.removeSubjectFromCourse(id, subjectId, actor);
            return new ResponseEntity<>("Materia removida com sucesso", HttpStatus.OK);
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
    @PatchMapping("/removeClass/{id}/{classeId}")
    public ResponseEntity<String> removeClassFromCourse(
            @Parameter(description = "Curso", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "Classe", required = true, example = "1") @PathVariable Long classeId
            /*@Parameter(description = "Usuário que adicionou o professor", required = true) @RequestParam User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();

        try {
            service.removeClassFromCourse(id, classeId, actor);
            return new ResponseEntity<>("Classe removida com sucesso", HttpStatus.OK);
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
            @Parameter(description = "ID do curso", required = true) @PathVariable Long id/*,
            @Parameter(description = "Usuário que adicionou o professor", required = true) @RequestParam User actor*/) {

        Admin actor = Admin.builder()
                .id(1L)
                .username("adminTrabalhandoCom@Senai")
                .password("adminConselho@estudante.com")
                .build();

        try {
            service.delete(id, actor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
