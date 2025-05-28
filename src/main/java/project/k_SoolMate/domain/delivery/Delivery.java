package project.k_SoolMate.domain.delivery;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.k_SoolMate.domain.address.Address;
import project.k_SoolMate.domain.order.Order;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    private Address address;

    @OneToOne(mappedBy = "delivery")
    private Order order;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
}
