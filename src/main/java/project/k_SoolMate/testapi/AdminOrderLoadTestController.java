package project.k_SoolMate.testapi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.k_SoolMate.domain.order.OrderService;
import project.k_SoolMate.domain.order.dto.OrderDTO;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class AdminOrderLoadTestController {

    private final OrderService orderService;
    private final AdminOrderLoadTestService adminOrderLoadTestService;

    @GetMapping("/order/before")
    public List<OrderDTO> before() {
        return orderService.getAllOrdersWithoutFetch();
    }

    @GetMapping("/order/after")
    public List<OrderDTO> after() {
        return orderService.getAllOrdersWithFetch();
    }

    @PostMapping("/order/bulk")
    public String bulkOrders() {
        adminOrderLoadTestService.bulkCreateOrders(1000);
        return "OK";
    }

}
