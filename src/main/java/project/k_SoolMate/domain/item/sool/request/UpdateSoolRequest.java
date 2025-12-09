package project.k_SoolMate.domain.item.sool.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "전통주 수정 요청 DTO")
public class UpdateSoolRequest {

    @Schema(description = "전통주 이름", example = "이강주 프리미엄")
    private String name;

    @Schema(description = "전통주 설명", example = "보다 깊은 풍미와 향을 가진 프리미엄 버전")
    private String description;

    @Schema(description = "도수(%)", example = "18.0")
    private double alcoholPercent;

    @Schema(description = "가격", example = "17000")
    private int price;

    @Schema(description = "재고 수량", example = "25")
    private int stockQuantity;

    @Schema(description = "원산지", example = "전북 남원")
    private String origin;

    @Schema(description = "브랜드명", example = "전통양조장 프리미엄")
    private String brand;
}
