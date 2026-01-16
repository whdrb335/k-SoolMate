package project.k_SoolMate.exception.item;

import org.springframework.http.HttpStatus;

public class UnauthorizedOrderCancelException extends ItemException {
    public UnauthorizedOrderCancelException(HttpStatus status) {
        super(status);
    }

    public UnauthorizedOrderCancelException(String message, HttpStatus status) {
        super(message, status);
    }

    public UnauthorizedOrderCancelException(String message, Throwable cause, HttpStatus status) {
        super(message, cause, status);
    }

    public UnauthorizedOrderCancelException(Throwable cause, HttpStatus status) {
        super(cause, status);
    }

    public UnauthorizedOrderCancelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace, status);
    }
}
