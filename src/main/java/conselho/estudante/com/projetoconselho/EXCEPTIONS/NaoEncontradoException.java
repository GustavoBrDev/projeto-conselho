<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/EXCEPTIONS/NaoEncontradoException.java
package conselho.estudante.com.projetoconselho.MODELS.EXCEPTIONS;
========
package conselho.estudante.com.projetoconselho.EXCEPTIONS;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/EXCEPTIONS/NaoEncontradoException.java

public class NaoEncontradoException extends RuntimeException {

    public NaoEncontradoException() {
    }

    public NaoEncontradoException(String message) {
        super(message);
    }

    public NaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

    public NaoEncontradoException(Throwable cause) {
        super(cause);
    }

    public NaoEncontradoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
