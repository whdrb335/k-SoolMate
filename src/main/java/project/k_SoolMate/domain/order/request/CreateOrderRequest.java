package project.k_SoolMate.domain.order.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.k_SoolMate.domain.address.Address;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "주문 생성 요청 DTO")
public class CreateOrderRequest {

    @Schema(description = "주문할 전통주의 ID", example = "101")
    private Long soolId;

    @Schema(description = "주문 수량 (최소 1개)", example = "2")
    @Min(value = 1, message = "최소 한개 이상은 주문하셔야합니다.")
    private int count;

    @Valid
    @Schema(description = "배송지 주소 정보")
    private Address address;
}
