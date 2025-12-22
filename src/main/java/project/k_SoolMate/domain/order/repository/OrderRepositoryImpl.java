package project.k_SoolMate.domain.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.k_SoolMate.domain.item.QOrderSool;
import project.k_SoolMate.domain.order.dto.OrderListDTO;

import java.util.List;

import static project.k_SoolMate.domain.order.entity.QOrder.order;
import static project.k_SoolMate.domain.user.entity.QUser.user;
import static project.k_SoolMate.domain.item.QOrderSool.orderSool;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<OrderListDTO> findOrderList(Pageable pageable) {

        List<OrderListDTO> content = queryFactory
                .select(Projections.constructor(
                        OrderListDTO.class,
                        order.id,
                        user.id,
                        user.name,
                        order.orderStatus, // ✅ 추가
                        orderSool.orderPrice
                                .multiply(orderSool.count)
                                .sum()
                                .coalesce(0), // ✅ 주문 금액
                        order.orderDate
                ))
                .from(order)
                .join(order.user, user)
                .leftJoin(order.orderSools, orderSool) // ✅ 반드시 필요
                .groupBy(
                        order.id,
                        user.id,
                        user.name,
                        order.orderStatus,
                        order.orderDate
                )
                .orderBy(order.orderDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(order.count())
                .from(order)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
