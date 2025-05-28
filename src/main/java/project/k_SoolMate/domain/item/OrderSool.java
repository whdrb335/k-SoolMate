package project.k_SoolMate.domain.item;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.k_SoolMate.domain.item.sool.Sool;
import project.k_SoolMate.domain.order.Order;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSool {
    @Id @GeneratedValue
    @Column(name = "order_sool_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;
    private int count;

    //==연관관계 메서드==//
    //양방향 매핑을 해야하는데 엔티티는 setter 열리지않아서 order에만 set해줌
    public void setOrder(Order order) {
        this.order = order;
    }
    //양방향 매핑을 해야하는데 엔티티는 setter 열리지않아서 item에만 set해줌
    public void setItem(Item item) {
        this.item = item;
    }

    //==생성 메서드==//

    /**
     * OrderSool 생성
     */
    public static OrderSool createOrderSool(Sool sool, int orderPrice, int count) {
        OrderSool orderSool = new OrderSool();
        orderSool.setItem(sool);
        orderSool.orderPrice = orderPrice;
        orderSool.count = count;
        sool.removeStock(count); // 주문시 재고 감소
        return orderSool;
    }

    /**
     * 취소시 재고증가
     */
    public void delete() {
        if (item instanceof Sool sool) {
            sool.addStock(count);
        }
    }

    /**
     * OrderSool 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() + getCount();
    }


}
