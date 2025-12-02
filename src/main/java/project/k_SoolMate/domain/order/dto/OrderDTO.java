package project.k_SoolMate.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.k_SoolMate.domain.delivery.dto.DeliveryDTO;
import project.k_SoolMate.domain.order.entity.Order;
import project.k_SoolMate.domain.order.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private OrderStatus orderStatus;

    private DeliveryDTO delivery;
    private List<OrderSoolDTO> orderSools;

    public OrderDTO(Order order) {
        this.orderId = order.getId();
        this.orderStatus = order.getOrderStatus();
        this.delivery = new DeliveryDTO(order.getDelivery());
        this.orderSools = order.getOrderSools().stream().map(OrderSoolDTO::new).toList();
    }
}
