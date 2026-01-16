package project.k_SoolMate.exception.order;

import org.springframework.http.HttpStatus;

public class NotFoundOrderException extends OrderException {
  public NotFoundOrderException(HttpStatus status) {
    super(status);
  }

  public NotFoundOrderException(String message, HttpStatus status) {
    super(message, status);
  }

  public NotFoundOrderException(String message, Throwable cause, HttpStatus status) {
    super(message, cause, status);
  }

  public NotFoundOrderException(Throwable cause, HttpStatus status) {
    super(cause, status);
  }

  public NotFoundOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
    super(message, cause, enableSuppression, writableStackTrace, status);
  }
}
