package project.k_SoolMate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.k_SoolMate.exception.item.ItemException;
import project.k_SoolMate.exception.order.OrderException;

@RestControllerAdvice
@Slf4j // 에러 로그를 찍기위해 이 어노테이션 사용
public class GlobalAdvice {

    //ItemException의 예외 전용
    @ExceptionHandler(ItemException.class)
    public ResponseEntity<ErrorResult> handlerItemException(ItemException e) {
        //에러 로그를 찍는 시점
        log.error("[ItemException] = {}", e.getMessage());
        // ErrorResult를 만들어서 에러를 똑같이 뿌린다.
        return new ResponseEntity<>(new ErrorResult("ITEM_EX", e.getMessage()), e.getStatus());
        //HttpStatus 마다 다른 status를 주기위해 status를 받아온다.
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ErrorResult> handlerOrderException(OrderException e) {
        log.error("[OrderException] = {}", e.getMessage());
        return new ResponseEntity<>(new ErrorResult("ORDER_EX", e.getMessage()), e.getStatus());
    }
}
