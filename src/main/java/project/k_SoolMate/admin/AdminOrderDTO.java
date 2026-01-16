package project.k_SoolMate.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.k_SoolMate.domain.address.Address;
import project.k_SoolMate.domain.order.dto.OrderSoolDTO;
import project.k_SoolMate.domain.order.entity.Order;
import project.k_SoolMate.domain.order.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class AdminOrderDTO {
    private Long orderId;
    private Long userId;
    private String userEmail;
    private OrderStatus status;
    private Address deliveryAddress;
    private LocalDateTime orderDate;
    private List<OrderSoolDTO> items;
    private int totalPrice;

    public AdminOrderDTO(Order order) {
        this.orderId = order.getId();
        this.userId = order.getUser().getId();
        this.userEmail = order.getUser().getEmail();
        this.status = order.getOrderStatus();
        this.deliveryAddress = order.getDelivery().getAddress();
        this.orderDate = order.getOrderDate();
        this.items = order.getOrderSools().stream().map(OrderSoolDTO::new).toList();
        this.totalPrice = order.getTotalPrice();
    }
}
