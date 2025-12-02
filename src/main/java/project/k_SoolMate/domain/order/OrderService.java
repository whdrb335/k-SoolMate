package project.k_SoolMate.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.k_SoolMate.domain.address.Address;
import project.k_SoolMate.domain.delivery.Delivery;
import project.k_SoolMate.domain.item.OrderSool;
import project.k_SoolMate.domain.item.sool.entity.Sool;
import project.k_SoolMate.domain.item.sool.repository.SoolRepository;
import project.k_SoolMate.domain.order.dto.OrderDTO;
import project.k_SoolMate.domain.order.entity.Order;
import project.k_SoolMate.domain.order.repository.OrderRepository;
import project.k_SoolMate.domain.order.request.CreateOrderReqeust;
import project.k_SoolMate.domain.user.entity.User;
import project.k_SoolMate.domain.user.repository.UserRepository;
import project.k_SoolMate.exception.item.NotFoundSoolException;
import project.k_SoolMate.exception.order.NotFoundOrderException;
import project.k_SoolMate.exception.user.NotFoundUserException;

@Service
// 기본적 DB 읽기전용 (최적화)
@Transactional(readOnly = true)
@RequiredArgsConstructor //final 필드 생성자 자동생성
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final SoolRepository soolRepository;

    /**
     * 오더 생성
     */
    @Transactional
    public OrderDTO createOrder(Long id, CreateOrderReqeust request) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundUserException("해당 사용자가 존재하지 않습니다.", HttpStatus.NOT_FOUND));
        Sool sool = soolRepository.findById(request.getSoolId()).orElseThrow(
                () -> new NotFoundSoolException("해당 술이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        Address address = new Address(request.getAddress().getCity(), request.getAddress().getStreet(), request.getAddress()
                .getZipcode());
        Delivery delivery = new Delivery(address);

        OrderSool orderSool = OrderSool.createOrderSool(sool, sool.getPrice(), request.getCount());

        Order order = Order.createOrder(user, delivery, orderSool);
        Order save = orderRepository.save(order);
        return new OrderDTO(save);

    }

    /**
     * 주문 취소
     */
    @Transactional
    public OrderDTO cancelOrder(Long id) {
        Order order = getOrderById(id);
        order.cancelOrder();
        return new OrderDTO(order);
    }



    private Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundOrderException("해당 주문은 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
    }
}
