package project.k_SoolMate.exception.order;

import org.springframework.http.HttpStatus;

public class AlreadyCancelOrder extends OrderException{
    public AlreadyCancelOrder(HttpStatus status) {
        super(status);
    }

    public AlreadyCancelOrder(String message, HttpStatus status) {
        super(message, status);
    }

    public AlreadyCancelOrder(String message, Throwable cause, HttpStatus status) {
        super(message, cause, status);
    }

    public AlreadyCancelOrder(Throwable cause, HttpStatus status) {
        super(cause, status);
    }

    public AlreadyCancelOrder(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace, status);
    }
}
