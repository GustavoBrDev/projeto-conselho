<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/EXCEPTIONS/DadosDuplicadosException.java
package conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS;
========
package conselho.estudante.com.projetoconselho.EXCEPTIONS;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/EXCEPTIONS/DadosDuplicadosException.java

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
