package project.k_SoolMate.domain.order.controller;

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
public class RestOrderController {
    private final OrderService orderService;

    /**
     * 주문 생성
     */
    @PostMapping("/create")
    public Result<OrderDTO> createOrder(@Validated @RequestBody CreateOrderRequest request, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
        OrderDTO order = orderService.createOrder(loginUser.getId(), request);
        return new Result<>(order);
    }

    /**
     * 주문 삭제
     */
    @DeleteMapping("/{orderId}")
    public Result<String> cancelOrder(@PathVariable("orderId") Long orderId, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
        orderService.cancelOrder(orderId, loginUser.getId());
        return new Result<>("삭제되었습니다.");
    }

    /**
     * 주문 조회(단건 조회)
     */
    @GetMapping("/{orderId}")
    public Result<OrderDTO> getOrder(@PathVariable("orderId") Long orderId, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
        OrderDTO order = orderService.getOrder(orderId, loginUser.getId());
        return new Result<>(order);
    }

    /**
     * 주문 전체조회
     */
    @GetMapping
    public Result<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> allOrders = orderService.getAllOrders();
        return new Result<>(allOrders);
    }
}
