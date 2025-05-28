package project.k_SoolMate.exception.user;

import org.springframework.http.HttpStatus;

public class DuplicateUserIdException extends UserException{
    public DuplicateUserIdException(HttpStatus status) {
        super(status);
    }

    public DuplicateUserIdException(String message, HttpStatus status) {
        super(message, status);
    }

    public DuplicateUserIdException(String message, Throwable cause, HttpStatus status) {
        super(message, cause, status);
    }

    public DuplicateUserIdException(Throwable cause, HttpStatus status) {
        super(cause, status);
    }

    public DuplicateUserIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace, status);
    }
}
