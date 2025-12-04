package project.k_SoolMate.domain.order.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.k_SoolMate.domain.address.Address;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    private Long soolId;
    @Min(value = 1, message = "최소 한개 이상은 주문하셔야합니다.")
    private int count;
    @Valid
    private Address address;

}
