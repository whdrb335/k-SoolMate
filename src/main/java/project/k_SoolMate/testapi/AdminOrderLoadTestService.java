package project.k_SoolMate.testapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.k_SoolMate.domain.address.Address;
import project.k_SoolMate.domain.delivery.Delivery;
import project.k_SoolMate.domain.item.OrderSool;
import project.k_SoolMate.domain.item.sool.entity.Sool;
import project.k_SoolMate.domain.item.sool.repository.SoolRepository;
import project.k_SoolMate.domain.order.entity.Order;
import project.k_SoolMate.domain.order.repository.OrderRepository;
import project.k_SoolMate.domain.user.entity.User;
import project.k_SoolMate.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AdminOrderLoadTestService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final SoolRepository soolRepository;

    @Transactional
    public void bulkCreateOrders(int count) {
        Sool sool = soolRepository.findById(1L).orElseThrow();
        User user = userRepository.findById(1L).orElseThrow();

        for (int i = 0; i < count; i++) {
            OrderSool orderSool =
                OrderSool.createOrderSoolForTest(sool, sool.getPrice(), 1);

            Order order = Order.createOrder(user, new Delivery(new Address("서울","관악","123")), orderSool);
            orderRepository.save(order);
        }
    }
}
