package project.k_SoolMate.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import project.k_SoolMate.domain.delivery.Delivery;
import project.k_SoolMate.domain.item.OrderSool;
import project.k_SoolMate.domain.user.entity.User;
import project.k_SoolMate.exception.order.AlreadyCancelOrder;

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

    //==생성 메서드==//

    /**
     * 주문 생성
     */
    public static Order createOrder(User user, Delivery delivery, OrderSool... orderSools) {
        Order order = new Order();
        order.user = user;
        order.delivery = delivery;
        order.orderStatus = OrderStatus.ORDER;
        for (OrderSool orderSool : orderSools) {
            order.addOrderSool(orderSool);
        }
        return order;
    }

    /**
     * 주문 취소
     */
    public void cancelOrder() {
        if (this.orderStatus == OrderStatus.CANCEL) {
            throw new AlreadyCancelOrder("이미 취소된 상품 입니다.", HttpStatus.BAD_REQUEST);
        }
        for (OrderSool orderSool : orderSools) {
            orderSool.delete();
        }
        this.orderStatus = OrderStatus.CANCEL;
    }

    /**
     * 주문 총 금액 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderSool orderSool : orderSools) {
            totalPrice+= orderSool.getTotalPrice();
        }
        return totalPrice;
    }

    /**
     * JPA 콜백 메서드로 생성/수정 시간 자동 설정
     */
    @PrePersist // 저장 전 자동으로 createAt, updatedAt 설정
    protected void onCreate() {
        this.orderDate = LocalDateTime.now();
    }
}
