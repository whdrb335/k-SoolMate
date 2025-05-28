package project.k_SoolMate.exception.user;

import org.springframework.http.HttpStatus;

public class NotFoundUserException extends UserException{
    public NotFoundUserException(HttpStatus status) {
        super(status);
    }

    public NotFoundUserException(String message, HttpStatus status) {
        super(message, status);
    }

    public NotFoundUserException(String message, Throwable cause, HttpStatus status) {
        super(message, cause, status);
    }

    public NotFoundUserException(Throwable cause, HttpStatus status) {
        super(cause, status);
    }

    public NotFoundUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace, status);
    }
}
