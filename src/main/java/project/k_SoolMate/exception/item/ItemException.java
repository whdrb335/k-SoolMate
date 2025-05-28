package project.k_SoolMate.exception.item;

import org.springframework.http.HttpStatus;

public class ItemException extends RuntimeException{
    private final HttpStatus status;
    public ItemException(HttpStatus status) {
        this.status = status;
    }

    public ItemException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ItemException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public ItemException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }

    public ItemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
