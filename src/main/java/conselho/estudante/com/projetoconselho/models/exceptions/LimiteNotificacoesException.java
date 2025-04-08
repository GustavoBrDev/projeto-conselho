package conselho.estudante.com.projetoconselho.models.exceptions;

/**
 * Exception para Limite de Notificacoes
 * @author Camilly Chelest
 * @since 12/03/2025
 */

public class LimiteNotificacoesException extends RuntimeException{
    public LimiteNotificacoesException() {
    }

    public LimiteNotificacoesException(String message) {
        super(message);
    }

    public LimiteNotificacoesException(String message, Throwable cause) {
        super(message, cause);
    }

    public LimiteNotificacoesException(Throwable cause) {
        super(cause);
    }

    public LimiteNotificacoesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
