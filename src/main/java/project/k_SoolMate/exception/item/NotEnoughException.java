package project.k_SoolMate.exception.item;

import org.springframework.http.HttpStatus;

public class NotEnoughException extends ItemException{
    public NotEnoughException(HttpStatus status) {
        super(status);
    }

    public NotEnoughException(String message, HttpStatus status) {
        super(message, status);
    }

    public NotEnoughException(String message, Throwable cause, HttpStatus status) {
        super(message, cause, status);
    }

    public NotEnoughException(Throwable cause, HttpStatus status) {
        super(cause, status);
    }

    public NotEnoughException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace, status);
    }
}
