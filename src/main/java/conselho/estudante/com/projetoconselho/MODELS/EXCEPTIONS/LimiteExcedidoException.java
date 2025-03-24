package conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS;

/**
 * Exception lançada quando um limite é excedido
 * @author Alex Zastrow
 */
public class LimiteExcedidoException extends RuntimeException {

    public LimiteExcedidoException() {
        super("Limite máximo foi excedido");
    }

    public LimiteExcedidoException(String message) {
        super(message);
    }

    public LimiteExcedidoException(String message, Throwable cause) {
        super(message, cause);
    }

    public LimiteExcedidoException(Throwable cause) {
        super(cause);
    }

    public LimiteExcedidoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}