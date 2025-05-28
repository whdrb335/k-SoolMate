package project.k_SoolMate.domain.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.k_SoolMate.domain.delivery.Delivery;
import project.k_SoolMate.domain.item.OrderSool;
import project.k_SoolMate.domain.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // 테이블 이름이 예약어랑 겹치기 때문에 바꿔준다
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // ToOne은 기본이 EAGER라서 LAZY로 잡아야 한다.
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL) // 오더가 취소되면 딜리버리도 없어지기때문에 cascade = all 이다.
    @JoinColumn(name = "delivery_id") //연관관계 주인을 order에 놓아서 @JoinColumn을 여기에 걸어주었다.
    private Delivery delivery;

    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderSool> orderSools = new ArrayList<>();

    //==연관관계 메서드==//

    public void setUser(User user) {
        this.user = user;
        user.getOrders().add(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public void addOrderSool(OrderSool orderSool) {
        orderSools.add(orderSool);
        orderSool.setOrder(this);
    }
    
}
