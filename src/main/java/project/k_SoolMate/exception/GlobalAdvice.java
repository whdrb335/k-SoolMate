package project.k_SoolMate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import project.k_SoolMate.exception.item.ItemException;
import project.k_SoolMate.exception.order.OrderException;
import project.k_SoolMate.exception.user.UserException;

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

    //User 예외도 여기서 ErrorResult로 공통 처리
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResult> handlerUserException(UserException e) {
        //에러가 발생시 에러 로그를 찍어볼수있게 로직 생성
        log.error("[UserException] = {}", e.getMessage());
        return new ResponseEntity<>(new ErrorResult("USER_EX", e.getMessage()), e.getStatus());
    }


    //나머지 각자의 공통으로 처리되는 로직을 이 밑에 코드로 처리
    //== 공통된 예외 처리==//
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("@Validated 검증을 다시 확인하세요!");
        return new ResponseEntity<>(new ErrorResult("ValidatedMethodError", errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResult> handleNPE(NullPointerException e) {
        log.error("[NullPointerException] = {}", e.getMessage());
        return new ResponseEntity<>(
                new ErrorResult("NULL_POINTER", "값이 비어 있어서 처리할 수 없습니다."),
                HttpStatus.BAD_REQUEST
    );
}

}
