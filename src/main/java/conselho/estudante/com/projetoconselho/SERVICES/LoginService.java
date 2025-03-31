package conselho.estudante.com.projetoconselho.SERVICES;

import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.LOGIN.FirstLoginResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.LOGIN.LoginResponse;
import conselho.estudante.com.projetoconselho.MODELS.DTO.RESPONSE.LOGIN.LoginResponseDTO;
import conselho.estudante.com.projetoconselho.MODELS.ENTITY.USERS.*;
import conselho.estudante.com.projetoconselho.SERVICES.ADMINISTRATION.ResetSessionService;
import conselho.estudante.com.projetoconselho.SERVICES.LOGS.LoginLogsService;
import conselho.estudante.com.projetoconselho.SERVICES.USERS.AdvisorService;
import conselho.estudante.com.projetoconselho.SERVICES.USERS.StudentService;
import conselho.estudante.com.projetoconselho.SERVICES.USERS.SupervisorService;
import conselho.estudante.com.projetoconselho.SERVICES.USERS.TECHNIQUE.TechniqueService;
import conselho.estudante.com.projetoconselho.SERVICES.USERS.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Classe de serviços para a autenticação de usuarios
 * Responsável por operações de login de usuarios
 * @author Gustavo Stinghen
 * @since 19/03/2025
 */

@Service
@AllArgsConstructor
public class LoginService {

    private StudentService studentService;
    private SupervisorService supervisorService;
    private TechniqueService techniqueService;
    private LoginLogsService loginLogsService;
    private TeacherService teacherService;
    private ResetSessionService resetSessionService;
    private AdvisorService advisorService;
    private EmailService emailService;

    /**
     * Método de login de usuarios
     * @param email o email do usuario
     * @param password a senha do usuario
     * @return um {@link LoginResponse} contendo o usuario e se ele foi autenticado
     */
    public LoginResponse login(String email, String password) {

        // Verifica se o usuario existe como estudante
        Student student = studentService.findObjectStudent(email);
        LoginResponse loginResponse;

        if (student != null) {

            // Verifica se a senha do estudante bate
            if (student.getPassword().equals(password)) {

                // Verifica se o estudante tem o primeiro login
                if ( ! loginLogsService.verifyFirstLogin( student ) ) {
                    loginResponse = FirstLoginResponseDTO.builder()
                            .user(student.convert())
                            .isFirstLogin(true)
                            .isAuthenticated(true)
                            .build();
                } else {
                    loginResponse = LoginResponseDTO.builder()
                            .user(student.convert())
                            .isAuthenticated(true)
                            .build();
                }

                loginLogsService.create( student );
                return loginResponse;

            } else {

                return LoginResponseDTO.builder()
                        .isAuthenticated(false)
                        .build();

            }
        }

        // Verifica se o usuario existe como tecnico
        Technique technique = techniqueService.findObjectTechnique(email);
        if (technique != null) {

            // Verifica se a senha do tecnico bate
            if (technique.getPassword().equals(password)) {

                // Verifica se o tecnico tem o primeiro login
                if ( ! loginLogsService.verifyFirstLogin( technique ) ) {
                    loginResponse = FirstLoginResponseDTO.builder()
                            .user(technique.toDTO())
                            .isFirstLogin(true)
                            .isAuthenticated(true)
                            .build();
                } else {
                    loginResponse = LoginResponseDTO.builder()
                            .user(technique.toDTO())
                            .isAuthenticated(true)
                            .build();
                }

                loginLogsService.create( technique );
                return loginResponse;

            } else {
                return LoginResponseDTO.builder()
                        .isAuthenticated(false)
                        .build();
            }
        }

        // Verifica se o usuario existe como supervisor
        Supervisor supervisor = supervisorService.findObjectSupervisor(email);
        if (supervisor != null) {

            // Verifica se a senha do supervisor bate
            if (supervisor.getPassword().equals(password)) {

                // Verifica se o supervisor tem o primeiro login
                if ( ! loginLogsService.verifyFirstLogin( supervisor ) ) {
                    loginResponse = FirstLoginResponseDTO.builder()
                            .user(supervisor.convert())
                            .isFirstLogin(true)
                            .isAuthenticated(true)
                            .build();
                } else {
                    loginResponse = LoginResponseDTO.builder()
                            .user(supervisor.convert())
                            .isAuthenticated(true)
                            .build();
                }

                loginLogsService.create( supervisor );
                return loginResponse;

            } else {
                return LoginResponseDTO.builder()
                        .isAuthenticated(false)
                        .build();
            }
        }

        Teacher teacher = teacherService.getObjectTeacher(email);

        // Verifica se o usuario existe como professor
        if (teacher != null) {

            // Verifica se a senha do professor bate
            if (teacher.getPassword().equals(password)) {

                // Verifica se o professor tem o primeiro login
                if ( ! loginLogsService.verifyFirstLogin( teacher ) ) {
                    loginResponse = FirstLoginResponseDTO.builder()
                            .user(teacher.toDTO())
                            .isFirstLogin(true)
                            .isAuthenticated(true)
                            .build();
                } else {
                    loginResponse = LoginResponseDTO.builder()
                            .user(teacher.toDTO())
                            .isAuthenticated(true)
                            .build();
                }

                loginLogsService.create( teacher );
                return loginResponse;

            } else {
                return LoginResponseDTO.builder()
                        .isAuthenticated(false)
                        .build();
            }
        }

        Advisor advisor = advisorService.getObjectAdvisor(email);

        // Verifica se o usuario existe como orientador
        if (advisor != null) {

            // Verifica se a senha do orientador bate
            if (advisor.getPassword().equals(password)) {

                // Verifica se o orientador tem o primeiro login
                if ( ! loginLogsService.verifyFirstLogin( advisor ) ) {
                    loginResponse = FirstLoginResponseDTO.builder()
                            .user(advisor.convert())
                            .isFirstLogin(true)
                            .isAuthenticated(true)
                            .build();
                } else {
                    loginResponse = LoginResponseDTO.builder()
                            .user(advisor.convert())
                            .isAuthenticated(true)
                            .build();
                }

                loginLogsService.create( advisor );
                return loginResponse;

            } else {
                return LoginResponseDTO.builder()
                        .isAuthenticated(false)
                        .build();
            }
        }

        // Se o usuario nao foi encontrado
        return LoginResponseDTO.builder()
                .isAuthenticated(false)
                .build();

    }

    /**
     * Método que envia um email para o usuario com um token para resetar a senha
     * @param email o email do usuario
     * @return se o email foi enviado
     */
    public boolean resetPassword(String email) {

        Student student = studentService.findObjectStudent(email);
        if ( student != null ) {

            String token = UUID.randomUUID().toString();

            if ( ! resetSessionService.create( student, token ) ) {
                throw new RuntimeException("Erro ao criar token" );
            }

            if (emailService.sendResetPasswordEmail(student.getEmail(), token)) {
                throw new RuntimeException("Erro ao enviar email" );
            }

            return true;

        }

        Technique technique = techniqueService.findObjectTechnique(email);
        if ( technique != null ) {

            String token = UUID.randomUUID().toString();

            if ( ! resetSessionService.create( technique, token ) ) {
                throw new RuntimeException("Erro ao criar token" );
            }

            if (emailService.sendResetPasswordEmail(technique.getEmail(), token)) {
                throw new RuntimeException("Erro ao enviar email" );
            }

            return true;

        }

        Supervisor supervisor = supervisorService.findObjectSupervisor(email);
        if ( supervisor != null ) {

            String token = UUID.randomUUID().toString();

            if ( ! resetSessionService.create( supervisor, token ) ) {
                throw new RuntimeException("Erro ao criar token" );
            }

            if (emailService.sendResetPasswordEmail(supervisor.getEmail(), token)) {
                throw new RuntimeException("Erro ao enviar email" );
            }

            return true;

        }

        return false;

    }

    /**
     * Método que altera a senha do usuario
     * @param token o token para resetar a senha
     * @param password a nova senha
     * @return se a senha foi alterada
     */
    public boolean changePassword(String token, String password) {

        if ( ! resetSessionService.existsByToken(token) ) {
            return false;
        }

        return resetSessionService.resetPasswordByToken(token, password);
    }


}
