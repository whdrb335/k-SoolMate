package project.k_SoolMate.domain.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.k_SoolMate.common.Result;
import project.k_SoolMate.domain.order.OrderService;
import project.k_SoolMate.domain.order.dto.OrderDTO;
import project.k_SoolMate.domain.order.request.CreateOrderRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@Tag(name = "Order API", description = "주문 생성 · 조회 · 취소 API")
public class RestOrderController {

    private final OrderService orderService;

    /**
     * 주문 생성
     */
    @Operation(
            summary = "주문 생성",
            description = "로그인한 사용자가 새로운 주문을 생성합니다."
    )
    @PostMapping("/create")
    public Result<OrderDTO> createOrder(
            @Validated @RequestBody CreateOrderRequest request,
            HttpServletRequest httpServletRequest) {

        Long userId = (Long) httpServletRequest.getAttribute("userId");
        OrderDTO order = orderService.createOrder(userId, request);
        return new Result<>(order);
    }

    /**
     * 주문 취소
     */
    @Operation(
            summary = "주문 취소",
            description = "로그인한 사용자가 자신의 주문을 취소합니다. (Soft Cancel 방식)"
    )
    @DeleteMapping("/{orderId}")
    public Result<String> cancelOrder(
            @PathVariable("orderId") Long orderId,
            HttpServletRequest httpServletRequest) {

        Long userId = (Long) httpServletRequest.getAttribute("userId");
        orderService.cancelOrder(orderId, userId);

        return new Result<>("삭제되었습니다.");
    }

    /**
     * 주문 조회(단건 조회)
     */
    @Operation(
            summary = "주문 단건 조회",
            description = "로그인한 사용자가 자신의 특정 주문 정보를 조회합니다."
    )
    @GetMapping("/{orderId}")
    public Result<OrderDTO> getOrder(
            @PathVariable("orderId") Long orderId,
            HttpServletRequest httpServletRequest) {

        Long userId = (Long) httpServletRequest.getAttribute("userId");

        OrderDTO order = orderService.getOrder(orderId, userId);
        return new Result<>(order);
    }


}
