package conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS;

public class CamposObrigatoriosException extends RuntimeException {
    public CamposObrigatoriosException(String message) {
        super(message);
    }
}