package project.k_SoolMate.domain.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import project.k_SoolMate.domain.address.Address;
import project.k_SoolMate.domain.delivery.Delivery;
import project.k_SoolMate.domain.item.OrderSool;
import project.k_SoolMate.domain.item.sool.dto.SoolDTO;
import project.k_SoolMate.domain.item.sool.entity.Sool;
import project.k_SoolMate.domain.item.sool.repository.SoolRepository;
import project.k_SoolMate.domain.item.sool.request.CreateSoolRequest;
import project.k_SoolMate.domain.item.sool.service.SoolService;
import project.k_SoolMate.domain.order.entity.Order;
import project.k_SoolMate.domain.order.entity.OrderStatus;
import project.k_SoolMate.domain.order.repository.OrderRepository;
import project.k_SoolMate.domain.order.request.CreateOrderReqeust;
import project.k_SoolMate.domain.user.dto.UserDTO;
import project.k_SoolMate.domain.user.entity.User;
import project.k_SoolMate.domain.user.repository.UserRepository;
import project.k_SoolMate.domain.user.request.CreateUserRequest;
import project.k_SoolMate.domain.user.service.UserService;
import project.k_SoolMate.exception.item.NotFoundSoolException;
import project.k_SoolMate.exception.order.NotFoundOrderException;
import project.k_SoolMate.exception.user.NotFoundUserException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserService userService;
    @Autowired
    SoolService soolService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SoolRepository soolRepository;

    @Test
    @DisplayName("주문 생성")
    public void 주문_생성() throws Exception {
        //given
        // == 유저 생성 == //
        CreateUserRequest userRequest = new CreateUserRequest(
                "whdrb3353",
                "1234",
                "김종규",
                "010-3582-9211",
                "whdrb3353@naver.com",
                new Address("서울","봉천로","4442"));
        UserDTO userDTO = userService.createUser(userRequest);
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new NotFoundUserException("해당 사용자가 존재하지 않습니다.", HttpStatus.NOT_FOUND));
        // == 술 생성 == //
        CreateSoolRequest soolRequest = new CreateSoolRequest(
                "막걸리",
                "정말맛있습니다",
                30,
                20000,
                10,
                "origin",
                "brand");
        SoolDTO soolDTO = soolService.createSool(soolRequest);
        Sool sool = soolRepository.findById(soolDTO.getId())
                .orElseThrow(() -> new NotFoundSoolException("해당 술이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        // == 주소 생성 == //
        Address address = new Address("서울", "관악구", "44144");
        // == 배달 생성 == //
        Delivery delivery = new Delivery(address);
        // == 주문할 술 생성 == //
        int originStock = sool.getStockQuantity();
        OrderSool orderSool = OrderSool.createOrderSool(sool, sool.getPrice(), 5);
        // == 주문 생성 == //
        Order order = Order.createOrder(user, delivery, orderSool);
        Order saveOrder = orderRepository.save(order);
        //when
        Order orderedOrder = orderRepository.findById(saveOrder.getId())
                .orElseThrow(() -> new NotFoundOrderException("해당 주문은 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        //then
        assertThat(orderedOrder.getOrderSools().get(0).getItem().getName()).isEqualTo("막걸리");
        assertThat(orderedOrder.getUser().getName()).isEqualTo("김종규");
        assertThat(orderedOrder.getDelivery().getAddress().getCity()).isEqualTo("서울");
        assertThat(sool.getStockQuantity()).isEqualTo(originStock - 5);

    }
    
    @Test
    @DisplayName("주문 삭제")
    public void 주문삭제후_status_cancel() throws Exception {
        //given
        // == 유저 생성 == //
        CreateUserRequest userRequest = new CreateUserRequest(
                "whdrb3353",
                "1234",
                "김종규",
                "010-3582-9211",
                "whdrb3353@naver.com",
                new Address("서울","봉천로","4442"));
        UserDTO userDTO = userService.createUser(userRequest);
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new NotFoundUserException("해당 사용자가 존재하지 않습니다.", HttpStatus.NOT_FOUND));
        // == 술 생성 == //
        CreateSoolRequest soolRequest = new CreateSoolRequest(
                "막걸리",
                "정말맛있습니다",
                30,
                20000,
                10,
                "origin",
                "brand");
        SoolDTO soolDTO = soolService.createSool(soolRequest);
        Sool sool = soolRepository.findById(soolDTO.getId())
                .orElseThrow(() -> new NotFoundSoolException("해당 술이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        int stockBeforeOrder = sool.getStockQuantity();
        // == 주소 생성 == //
        Address address = new Address("서울", "관악구", "44144");
        // == 배달 생성 == //
        Delivery delivery = new Delivery(address);
        // == 주문할 술 생성 == //
        OrderSool orderSool = OrderSool.createOrderSool(sool, sool.getPrice(), 5);

        assertThat(sool.getStockQuantity()).isEqualTo(stockBeforeOrder - 5);
        // == 주문 생성 == //
        Order order = Order.createOrder(user, delivery, orderSool);
        Order saveOrder = orderRepository.save(order);//when
        saveOrder.cancelOrder();
        Order orderedOrder = orderRepository.findById(saveOrder.getId())
                .orElseThrow(() -> new NotFoundOrderException("해당 주문은 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        //then
        assertThat(orderedOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(sool.getStockQuantity()).isEqualTo(stockBeforeOrder);
    }
}
