package conselho.estudante.com.projetoconselho.models.exceptions;

/**
 * Classe de tratamento de exceções de email
 * @author Gustavo Stinghen
 * @since 07/04/2025
 */

public class EmailException extends RuntimeException{

    public EmailException() {
    }

    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailException(Throwable cause) {
        super(cause);
    }

    public EmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
