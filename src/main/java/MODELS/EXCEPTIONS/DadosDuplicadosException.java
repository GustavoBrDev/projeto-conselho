package MODELS.EXCEPTIONS;

/**
 * Exception para dados duplicados
 * @author Camilly Chelest
 * @since 12/03/2025
 */

public class DadosDuplicadosException extends RuntimeException{

    public DadosDuplicadosException() {
    }

    public DadosDuplicadosException(String message) {
        super(message);
    }

    public DadosDuplicadosException(String message, Throwable cause) {
        super(message, cause);
    }

    public DadosDuplicadosException(Throwable cause) {
        super(cause);
    }

    public DadosDuplicadosException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
