package project.k_SoolMate.domain.item;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    private int OrderPrice;
    private int count;

    //==연관관계 메서드==//
    //양방향 매핑을 해야하는데 엔티티는 setter 열리지않아서 order에만 set해줌
    public void setOrder(Order order) {
        this.order = order;
    }
}
