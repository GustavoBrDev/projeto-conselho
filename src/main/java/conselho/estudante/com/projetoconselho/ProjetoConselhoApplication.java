package conselho.estudante.com.projetoconselho;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@OpenAPIDefinition(
    info = @io.swagger.v3.oas.annotations.info.Info(
        title = "Projeto Conselho",
        version = "1.0",
        description = "Sistema de gerenciamento de conselhos de classe para o SENAI"
))
public class ProjetoConselhoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetoConselhoApplication.class, args);
    }

}
