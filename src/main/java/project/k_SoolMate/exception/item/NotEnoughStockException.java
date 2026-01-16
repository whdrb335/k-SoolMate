package project.k_SoolMate.exception.item;

import org.springframework.http.HttpStatus;

public class NotEnoughStockException extends ItemException{
    public NotEnoughStockException(HttpStatus status) {
        super(status);
    }

    public NotEnoughStockException(String message, HttpStatus status) {
        super(message, status);
    }

    public NotEnoughStockException(String message, Throwable cause, HttpStatus status) {
        super(message, cause, status);
    }

    public NotEnoughStockException(Throwable cause, HttpStatus status) {
        super(cause, status);
    }

    public NotEnoughStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace, status);
    }
}
