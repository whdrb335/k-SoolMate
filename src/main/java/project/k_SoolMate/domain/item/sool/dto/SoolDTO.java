package project.k_SoolMate.domain.item.sool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.k_SoolMate.domain.item.ItemStatus;
import project.k_SoolMate.domain.item.ItemType;
import project.k_SoolMate.domain.item.sool.entity.Sool;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "전통주 상품 정보 DTO")
public class SoolDTO {

    @Schema(description = "전통주 ID", example = "101")
    private Long id;

    @Schema(description = "상품명", example = "이강주 750ml")
    private String name;

    @Schema(description = "상품 설명", example = "맑고 깔끔한 향의 전통 소주")
    private String description;

    @Schema(description = "도수(%)", example = "17.5")
    private double alcoholPercent;

    @Schema(description = "가격", example = "15000")
    private int price;

    @Schema(description = "재고 수량", example = "20")
    private int stockQuantity;

    @Schema(description = "원산지", example = "전북 남원")
    private String origin;

    @Schema(description = "브랜드명", example = "전통양조장")
    private String brand;

    @Schema(description = "상품 타입", example = "SOOL")
    private ItemType itemType;

    @Schema(description = "상품 상태(ACTIVE / DELETED)", example = "ACTIVE")
    private ItemStatus itemStatus;


    public SoolDTO(Sool sool) {
        this.id = sool.getId();
        this.name = sool.getName();
        this.description = sool.getDescription();
        this.alcoholPercent = sool.getAlcoholPercent();
        this.price = sool.getPrice();
        this.stockQuantity = sool.getStockQuantity();
        this.origin = sool.getOrigin();
        this.brand = sool.getBrand();
        this.itemType = sool.getItemType();
        this.itemStatus = sool.getItemStatus();
    }
}
