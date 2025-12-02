package project.k_SoolMate.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.k_SoolMate.domain.item.OrderSool;
import project.k_SoolMate.domain.item.sool.entity.Sool;

@Getter
@AllArgsConstructor
public class OrderSoolDTO {
    private Long soolId;
    private String name;
    private int orderPrice;
    private int count;
    private int totalPrice; // option (보여주기 좋음)
    private String brand;
    private double alcoholPercent;

    public OrderSoolDTO(OrderSool orderSool) {
        Sool sool = (Sool) orderSool.getItem();
        this.soolId = sool.getId();
        this.name = sool.getName();
        this.orderPrice = orderSool.getOrderPrice();
        this.count = orderSool.getCount();
        this.totalPrice = orderSool.getOrderPrice() * orderSool.getCount();
        // sool 엔티티에서 필요한 정보 추출
        this.brand = sool.getBrand();
        this.alcoholPercent = sool.getAlcoholPercent();
    }
}
