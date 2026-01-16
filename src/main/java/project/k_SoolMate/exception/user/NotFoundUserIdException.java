package project.k_SoolMate.exception.user;

import org.springframework.http.HttpStatus;

public class NotFoundUserIdException extends UserException{
    public NotFoundUserIdException(HttpStatus status) {
        super(status);
    }

    public NotFoundUserIdException(String message, HttpStatus status) {
        super(message, status);
    }

    public NotFoundUserIdException(String message, Throwable cause, HttpStatus status) {
        super(message, cause, status);
    }

    public NotFoundUserIdException(Throwable cause, HttpStatus status) {
        super(cause, status);
    }

    public NotFoundUserIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace, status);
    }
}
