package project.k_SoolMate.exception.user;

import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException{
    private final HttpStatus status;

    public UserException(HttpStatus status) {
        super();
        this.status = status;
    }

    public UserException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public UserException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public UserException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }

    protected UserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
