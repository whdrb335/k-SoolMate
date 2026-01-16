package project.k_SoolMate.exception.item;

import org.springframework.http.HttpStatus;

public class NotFoundSoolException extends ItemException {
    public NotFoundSoolException(HttpStatus status) {
        super(status);
    }

    public NotFoundSoolException(String message, HttpStatus status) {
        super(message, status);
    }

    public NotFoundSoolException(String message, Throwable cause, HttpStatus status) {
        super(message, cause, status);
    }

    public NotFoundSoolException(Throwable cause, HttpStatus status) {
        super(cause, status);
    }

    public NotFoundSoolException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace, status);
    }
}
