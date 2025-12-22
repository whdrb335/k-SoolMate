package project.k_SoolMate.domain.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.k_SoolMate.domain.order.dto.OrderListDTO;

public interface OrderRepositoryCustom {
    Page<OrderListDTO> findOrderList(Pageable pageable);
}
