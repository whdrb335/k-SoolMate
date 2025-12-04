package project.k_SoolMate.exception.item;

import org.springframework.http.HttpStatus;

public class OrderOwnerMismatchException extends ItemException {
    public OrderOwnerMismatchException(HttpStatus status) {
        super(status);
    }

    public OrderOwnerMismatchException(String message, HttpStatus status) {
        super(message, status);
    }

    public OrderOwnerMismatchException(String message, Throwable cause, HttpStatus status) {
        super(message, cause, status);
    }

    public OrderOwnerMismatchException(Throwable cause, HttpStatus status) {
        super(cause, status);
    }

    public OrderOwnerMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace, status);
    }
}
