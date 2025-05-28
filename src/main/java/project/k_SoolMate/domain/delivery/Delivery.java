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

    //==연관관계 메서드==
    //양방향 매핑을 해야하는데 엔티티는 setter 열리지않아서 order에만 set해줌
    public void setOrder(Order order) {
        this.order = order;
    }

    //==생성 메서드==//
    public static Delivery createDelivery(Address address) {
        Delivery delivery = new Delivery();
        delivery.address = address;
        delivery.deliveryStatus = DeliveryStatus.READY;
        return delivery;
    }
}
