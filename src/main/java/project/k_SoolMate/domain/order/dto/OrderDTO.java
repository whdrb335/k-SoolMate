package project.k_SoolMate.domain.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import project.k_SoolMate.domain.delivery.dto.DeliveryDTO;
import project.k_SoolMate.domain.order.entity.Order;
import project.k_SoolMate.domain.order.entity.OrderStatus;

import java.util.List;

@Getter
@AllArgsConstructor
@Schema(description = "주문 정보 DTO")
public class OrderDTO {

    @Schema(description = "주문 ID", example = "101")
    private Long orderId;

    @Schema(description = "주문 상태", example = "ORDER")
    private OrderStatus orderStatus;

    @Schema(description = "주문자 이름", example = "김종규")
    private String userName;

    @Schema(description = "배송 정보")
    private DeliveryDTO delivery;

    @Schema(description = "주문된 전통주 목록")
    private List<OrderSoolDTO> orderSools;

    public OrderDTO(Order order) {
        this.orderId = order.getId();
        this.orderStatus = order.getOrderStatus();
        this.userName = order.getUser().getName();
        this.delivery = new DeliveryDTO(order.getDelivery());
        this.orderSools = order.getOrderSools().stream()
                .map(OrderSoolDTO::new)
                .toList();
    }
}
