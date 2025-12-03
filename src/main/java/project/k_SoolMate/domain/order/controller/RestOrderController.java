package project.k_SoolMate.domain.order.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.k_SoolMate.common.Result;
import project.k_SoolMate.domain.order.OrderService;
import project.k_SoolMate.domain.order.dto.OrderDTO;
import project.k_SoolMate.domain.order.entity.OrderStatus;
import project.k_SoolMate.domain.order.request.CreateOrderReqeust;
import project.k_SoolMate.domain.user.dto.UserDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class RestOrderController {
    private final OrderService orderService;

    /**
     * 주문 생성
     */
    @PostMapping("/create")
    public Result<OrderDTO> createOrder(@Validated @RequestBody CreateOrderReqeust reqeust, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
        OrderDTO order = orderService.createOrder(loginUser.getId(), reqeust);
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

}
