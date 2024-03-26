package jpabook.jpashop2.repository.order.query;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;


    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> list= findOrders();

        list.forEach(o -> {
          List<OrderItemQueryDto> orderitem=  findOrderItems(o.getOrderId());
          o.setOrderItems(orderitem);
        });

        return list;
    }

    public List<OrderQueryDto>  findAllByDto_optimization(){
        List<OrderQueryDto> result = findOrders();
        List<Long> orderIds  = result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());  // 주문 한 놈 들 id 다가져오고

        List<OrderItemQueryDto> orderItems =  em.createQuery(
                        "select new jpabook.jpashop2.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                                " from OrderItem oi  "+
                                " join oi.item i " +
                                " where oi.order.id in :orderIds ", OrderItemQueryDto.class)
                .setParameter("orderIds",orderIds)
                .getResultList();
        //파라미타 셋팅

        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream().collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
        // 키값 id 주문아이템 리스트 분리
        result.forEach(o-> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;

    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
                "select new jpabook.jpashop2.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                        " from OrderItem oi  "+
                        " join oi.item i " +
                        " where oi.order.id = : orderId ", OrderItemQueryDto.class)
                .setParameter("orderId",orderId)
                .getResultList();

    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                " select new jpabook.jpashop2.repository.order.query.OrderQueryDto(o.id, m.username, o.orderDate, o.status, d.address) " +
                        " from Orders o " +
                        " join o.member m " +
                        " join o.delivery d ", OrderQueryDto.class).getResultList();
    }

    public List<OrderFlatDto> findAllByDto_flat(){
        return  em.createQuery(
                " select new " +
                        " jpabook.jpashop2.repository.order.query.OrderFlatDto(o.id,m.username,o.orderDate,o.status,d.address, i.name, oi.orderPrice,oi.count) " +
                        " from Orders o " +
                        " join o.member m " +
                        " join o.delivery d " +
                        " join o.orderItems oi " +
                        " join oi.item i ", OrderFlatDto.class)
                .getResultList();

    }


}
