package project.k_SoolMate.domain.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.k_SoolMate.domain.address.Address;
import project.k_SoolMate.domain.delivery.Delivery;
import project.k_SoolMate.domain.item.OrderSool;
import project.k_SoolMate.domain.user.entity.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderReqeust {
    private Long soolId;
    private int count;
    private Address address;

}
