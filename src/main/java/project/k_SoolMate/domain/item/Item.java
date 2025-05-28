package project.k_SoolMate.domain.item;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//모든 자식타입을 한번에 처리 왜? 조회빠르고 단순해서 나중에 Join으로 확장시킬 생각
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_type")
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private String description;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    @OneToMany(mappedBy = "item")
    private List<OrderSool> orderSools = new ArrayList<>();

    //==연관관계 메서드==//

    public void addOrderSool(OrderSool orderSool) {
        orderSools.add(orderSool);
        orderSool.setItem(this);
    }
}
