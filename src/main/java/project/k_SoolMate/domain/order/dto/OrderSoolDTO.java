package project.k_SoolMate.domain.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import project.k_SoolMate.domain.item.OrderSool;

@Getter
@AllArgsConstructor
@Schema(description = "주문된 전통주 상세 정보 DTO")
public class OrderSoolDTO {

    @Schema(description = "술(ID)", example = "10")
    private Long soolId;

    @Schema(description = "상품 이름", example = "이강주 750ml")
    private String itemName;

    @Schema(description = "주문 당시 단가", example = "15000")
    private int orderPrice;

    @Schema(description = "주문 수량", example = "2")
    private int count;

    @Schema(description = "총 가격 (단가 × 수량)", example = "30000")
    private int totalPrice;

    @Schema(description = "브랜드명", example = "전통양조장")
    private String brand;

    @Schema(description = "도수(%)", example = "17.5")
    private double alcoholPercent;

    public OrderSoolDTO(OrderSool orderSool) {
        this.soolId = orderSool.getItem().getId();
        this.itemName = orderSool.getItem().getName();
        this.orderPrice = orderSool.getOrderPrice();
        this.count = orderSool.getCount();
        this.totalPrice = orderSool.getOrderPrice() * orderSool.getCount();
        this.brand = orderSool.getItem().getBrand();
        this.alcoholPercent = orderSool.getItem().getAlcoholPercent();
    }
}
