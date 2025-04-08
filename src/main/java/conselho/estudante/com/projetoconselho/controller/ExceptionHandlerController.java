package conselho.estudante.com.projetoconselho.controller;

import conselho.estudante.com.projetoconselho.models.dto.response.ErrorResponse;
import conselho.estudante.com.projetoconselho.models.exceptions.CamposObrigatoriosException;
import conselho.estudante.com.projetoconselho.models.exceptions.DadosDuplicadosException;
import conselho.estudante.com.projetoconselho.models.exceptions.DataInvalidaException;
import conselho.estudante.com.projetoconselho.models.exceptions.NaoEncontradoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

/**
 * Classe de tratamento de exceções de controller
 * @author Gustavo Stinghen
 * @since 07/04/2025
 */

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(NaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleNaoEncontradoException(NaoEncontradoException e) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .timestamp( Instant.now())
                .type( "NaoEncontradoException")
                .build();

        return ResponseEntity.status(404).body(errorResponse);

    }

    @ExceptionHandler(DadosDuplicadosException.class)
    public ResponseEntity<ErrorResponse> handleDadosDuplicadosException(DadosDuplicadosException e) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .timestamp( Instant.now())
                .type( "DadosDuplicadosException")
                .build();

        return ResponseEntity.status(400).body(errorResponse);

    }

    @ExceptionHandler(CamposObrigatoriosException.class)
    public ResponseEntity<ErrorResponse> handleCamposObrigatoriosException(CamposObrigatoriosException e) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .timestamp( Instant.now())
                .type( "CamposObrigatoriosException")
                .build();

        return ResponseEntity.status(412).body(errorResponse);
    }

    @ExceptionHandler( DataInvalidaException.class )
    public ResponseEntity<ErrorResponse> handleDataInvalidaException( DataInvalidaException e ) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message( e.getMessage() )
                .timestamp( Instant.now() )
                .type( "DataInvalidaException" )
                .build();

        return ResponseEntity.status( 400 ).body( errorResponse );
    }

    @ExceptionHandler( RuntimeException.class )
    public ResponseEntity<ErrorResponse> handleException( RuntimeException e ) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message( e.getMessage() )
                .timestamp( Instant.now() )
                .type( "Exception" )
                .build();

        e.printStackTrace();
        System.out.println( errorResponse.toString() );
        return ResponseEntity.status( 500 ).body( errorResponse );
    }

}
