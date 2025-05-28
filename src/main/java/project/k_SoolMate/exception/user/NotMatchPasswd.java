package project.k_SoolMate.exception.user;

import org.springframework.http.HttpStatus;

public class NotMatchPasswd extends UserException{
    public NotMatchPasswd(HttpStatus status) {
        super(status);
    }

    public NotMatchPasswd(String message, HttpStatus status) {
        super(message, status);
    }

    public NotMatchPasswd(String message, Throwable cause, HttpStatus status) {
        super(message, cause, status);
    }

    public NotMatchPasswd(Throwable cause, HttpStatus status) {
        super(cause, status);
    }

    public NotMatchPasswd(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace, status);
    }
}
