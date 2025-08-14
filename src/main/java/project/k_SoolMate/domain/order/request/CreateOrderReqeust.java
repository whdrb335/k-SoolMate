package project.k_SoolMate.domain.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.k_SoolMate.domain.item.OrderSool;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderReqeust {
    private List<OrderSool> sools;
}
