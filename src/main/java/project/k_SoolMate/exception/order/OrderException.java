package project.k_SoolMate.exception.order;

import org.springframework.http.HttpStatus;

public class OrderException extends RuntimeException{
    private final HttpStatus status;

    public OrderException(HttpStatus status) {
        super();
        this.status = status;
    }

    public OrderException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public OrderException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public OrderException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }

    protected OrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }
}
