package project.k_SoolMate.domain.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.k_SoolMate.common.Result;
import project.k_SoolMate.domain.order.OrderService;
import project.k_SoolMate.domain.order.dto.OrderDTO;
import project.k_SoolMate.domain.order.request.CreateOrderRequest;
import project.k_SoolMate.domain.user.dto.UserDTO;

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

        HttpSession session = httpServletRequest.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");

        OrderDTO order = orderService.createOrder(loginUser.getId(), request);
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

        HttpSession session = httpServletRequest.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");

        orderService.cancelOrder(orderId, loginUser.getId());

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

        HttpSession session = httpServletRequest.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");

        OrderDTO order = orderService.getOrder(orderId, loginUser.getId());
        return new Result<>(order);
    }

    /**
     * 주문 전체 조회
     */
    @Operation(
            summary = "전체 주문 조회 (ADMIN 전용)",
            description = "모든 주문 내역을 조회합니다. 관리자만 호출 가능하도록 설계하는 것이 일반적입니다."
    )
    @GetMapping
    public Result<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> allOrders = orderService.getAllOrders();
        return new Result<>(allOrders);
    }
}
