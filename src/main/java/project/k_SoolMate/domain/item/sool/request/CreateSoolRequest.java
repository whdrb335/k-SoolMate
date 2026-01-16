package project.k_SoolMate.domain.item.sool.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "전통주 등록 요청 DTO")
public class CreateSoolRequest {

    @Schema(description = "전통주 이름", example = "이강주")
    @NotBlank(message = "술 이름은 필수 입니다.")
    private String name;

    @Schema(description = "전통주 설명", example = "맑고 깨끗한 향과 깔끔한 맛의 전통 소주")
    @NotBlank(message = "설명은 필수 입니다.")
    private String description;

    @Schema(description = "도수(%)", example = "17.5")
    private double alcoholPercent;

    @Schema(description = "가격", example = "15000")
    private int price;

    @Schema(description = "재고 수량", example = "10")
    @Min(value = 1, message = "최소 1개 이상입니다.")
    private int stockQuantity;

    @Schema(description = "원산지", example = "전북 남원")
    private String origin;

    @Schema(description = "브랜드명", example = "전통양조장")
    private String brand;
}
