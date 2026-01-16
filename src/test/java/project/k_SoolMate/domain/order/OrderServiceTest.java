package project.k_SoolMate.domain.order;

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
import project.k_SoolMate.domain.order.dto.OrderDTO;
import project.k_SoolMate.domain.order.entity.Order;
import project.k_SoolMate.domain.order.entity.OrderStatus;
import project.k_SoolMate.domain.order.repository.OrderRepository;
import project.k_SoolMate.domain.user.dto.UserDTO;
import project.k_SoolMate.domain.user.entity.User;
import project.k_SoolMate.domain.user.repository.UserRepository;
import project.k_SoolMate.domain.user.request.CreateUserRequest;
import project.k_SoolMate.domain.user.service.UserService;
import project.k_SoolMate.exception.item.NotFoundSoolException;
import project.k_SoolMate.exception.item.OrderOwnerMismatchException;
import project.k_SoolMate.exception.item.UnauthorizedOrderCancelException;
import project.k_SoolMate.exception.order.NotFoundOrderException;
import project.k_SoolMate.exception.user.NotFoundUserException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
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

    @Test
    @DisplayName("주문 삭제_본인주문아닐시예외처리")
    public void 주문_삭제_본인주문아닐시_예외() throws Exception {
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

        // 다른 유저
        CreateUserRequest anotherUser = new CreateUserRequest(
                "whdrb3354",
                "1234",
                "김종규",
                "010-3582-9211",
                "whdrb3353@naver.com",
                new Address("서울","봉천로","4442"));
        UserDTO anotherUserDTO = userService.createUser(anotherUser);
        User user2 = userRepository.findById(anotherUserDTO.getId())
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

        //then
        assertThatThrownBy(() -> orderService.cancelOrder(saveOrder.getId(), user2.getId()))
                .isInstanceOf(UnauthorizedOrderCancelException.class)
                .hasMessageContaining("해당 주문은 본인의 주문이 아닙니다.",HttpStatus.UNAUTHORIZED);
    }
    
    @Test
    @DisplayName("주문 단건 조회")
    public void 주문_단건_조회() throws Exception {
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
        OrderDTO result = orderService.getOrder(saveOrder.getId(), user.getId());
        //then
        assertThat(result.getUserName()).isEqualTo("김종규");
        assertThat(result.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(result.getOrderSools().get(0).getItemName()).isEqualTo("막걸리");

    }

    @Test
    @DisplayName("주문 단건 조회_예외_본인아닐시")
    public void 주문_단건_조회_예외_본인아닐시() throws Exception {
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
        // == 다른 유저 생성
        CreateUserRequest userRequest1 = new CreateUserRequest(
                "whdrb3355",
                "1234",
                "김종규",
                "010-3582-9211",
                "whdrb3353@naver.com",
                new Address("서울","봉천로","4442"));
        UserDTO userDTO1 = userService.createUser(userRequest1);
        User user1 = userRepository.findById(userDTO1.getId())
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
        //then
        assertThatThrownBy(() -> orderService.getOrder(saveOrder.getId(), user1.getId()))
                .isInstanceOf(OrderOwnerMismatchException.class)
                .hasMessageContaining("주문에 접근할 권한이 없습니다.", HttpStatus.UNAUTHORIZED);
    }


    @Test
    @DisplayName("주문 전체 조회")
    public void 주문_전체_조회() throws Exception {
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
        // == 유저 2 생성
        CreateUserRequest userRequest1 = new CreateUserRequest(
                "whdrb3354",
                "1234",
                "현예담",
                "010-3582-9211",
                "whdrb3353@naver.com",
                new Address("서울","봉천로","4442"));
        UserDTO userDTO1 = userService.createUser(userRequest1);
        User user1 = userRepository.findById(userDTO1.getId())
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

        // == 술2 생성 == //
        CreateSoolRequest soolRequest1 = new CreateSoolRequest(
                "소주",
                "정말맛있습니다",
                30,
                20000,
                10,
                "origin",
                "brand");
        SoolDTO soolDTO1 = soolService.createSool(soolRequest1);
        Sool sool1 = soolRepository.findById(soolDTO1.getId())
                .orElseThrow(() -> new NotFoundSoolException("해당 술이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        // == 주소 생성 == //
        Address address = new Address("서울", "관악구", "44144");
        Address address1 = new Address("대구", "수성구", "44144");
        // == 배달 생성 == //
        Delivery delivery = new Delivery(address);
        Delivery delivery1 = new Delivery(address1);
        // == 주문할 술 생성 == //
        int originStock = sool.getStockQuantity();
        OrderSool orderSool = OrderSool.createOrderSool(sool, sool.getPrice(), 5);
        OrderSool orderSool1 = OrderSool.createOrderSool(sool1, sool1.getPrice(), 10);
        // == 주문 생성 == //
        Order order = Order.createOrder(user, delivery, orderSool);
        Order order1 = Order.createOrder(user1, delivery1, orderSool1);
        Order saveOrder = orderRepository.save(order);
        Order saveOrder1 = orderRepository.save(order1);
        List<OrderDTO> allOrders = orderService.getAllOrders();
        //then
        assertThat(allOrders.size()).isEqualTo(2);
    }
}
