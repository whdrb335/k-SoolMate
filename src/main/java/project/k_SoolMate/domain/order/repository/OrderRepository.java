package project.k_SoolMate.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.k_SoolMate.domain.order.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * 주문 전체 조회 (N+1 문제 방지용 fetch join)
     * Order + Delivery + OrderSool + Item 을 한 번에 로딩
     */
    @Query("select distinct o from Order o "+
            "join fetch o.delivery d "+
            "join fetch o.orderSools os "+
            "join fetch os.item i")
    List<Order> findAllWithItems();

    /**
     * 내 주문 전체 조회 (N+1 문제 방지용 fetch join)
     * Order + Delivery + OrderSool + Item 을 한 번에 로딩
     */
    @Query("select distinct o from Order o "+
            "join fetch o.delivery d " +
            "join fetch o.orderSools os "+
            "join fetch os.item i "+
            "where o.user.id = :userId")
    List<Order> findAllWithItemsByUserId(Long userId);
}
