package conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS;

public class DataInvalidaException extends RuntimeException {
    public DataInvalidaException(String message) {
        super(message);
    }
}