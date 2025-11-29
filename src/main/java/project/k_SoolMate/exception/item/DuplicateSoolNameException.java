package project.k_SoolMate.exception.item;

import org.springframework.http.HttpStatus;

public class DuplicateSoolNameException extends ItemException {
    public DuplicateSoolNameException(HttpStatus status) {
        super(status);
    }

    public DuplicateSoolNameException(String message, HttpStatus status) {
        super(message, status);
    }

    public DuplicateSoolNameException(String message, Throwable cause, HttpStatus status) {
        super(message, cause, status);
    }

    public DuplicateSoolNameException(Throwable cause, HttpStatus status) {
        super(cause, status);
    }

    public DuplicateSoolNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace, status);
    }
}
