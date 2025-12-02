package project.k_SoolMate.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.k_SoolMate.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
