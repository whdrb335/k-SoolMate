package project.k_SoolMate.domain.order.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import project.k_SoolMate.domain.order.entity.OrderStatus;

import java.time.LocalDateTime;

@Getter
public class OrderListDTO {
    private Long orderId;
    private Long userId;
    private String userName;
    private OrderStatus orderStatus;
    private int totalPrice;
    private LocalDateTime createdAt;
    public OrderListDTO(Long orderId,
                        Long userId,
                        String userName,
                        OrderStatus orderStatus,
                        int totalPrice,
                        LocalDateTime createdAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.userName = userName;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }
}
