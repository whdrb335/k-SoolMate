package project.k_SoolMate.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private String userName;
    private LocalDateTime orderDate;
}
