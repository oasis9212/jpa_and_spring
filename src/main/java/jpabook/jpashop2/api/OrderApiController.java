package jpabook.jpashop2.api;


import jpabook.jpashop2.domain.Address;
import jpabook.jpashop2.domain.OrderItem;
import jpabook.jpashop2.domain.OrderStatus;
import jpabook.jpashop2.domain.Orders;
import jpabook.jpashop2.repository.OrderRepository;
import jpabook.jpashop2.repository.OrderSearch;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("api/v1/orders")
    public List<Orders> ordersV1(){
          List<Orders> all= orderRepository.findAll(new OrderSearch());
        for(Orders orders: all){
            orders.getMember().getUsername();
            orders.getDelivery().getAddress();
            List<OrderItem> orderItems= orders.getOrderItems();
            orderItems.stream().forEach(o-> o.getItem().getName());
        }
        return  all;
    }

    @GetMapping("api/v2/orders")
    public List<OrdersDto> ordersV2(){
        List<Orders> all= orderRepository.findAll(new OrderSearch());
        List<OrdersDto>  list= all.stream()
                                .map(o-> new OrdersDto(o))
                                .collect(Collectors.toList());
        return list;
    }

    @Data
    static class OrdersDto{

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        private OrdersDto(Orders orders ){
            orderId=orders.getId();
            name= orders.getMember().getUsername();
            orderDate= orders.getOrderDate();
            orderStatus=orders.getStatus();
            address=orders.getDelivery().getAddress();
            // 엔티티의 소스 파악을 불가능하게 해야한다.
//            orders.getOrderItems().stream().forEach( o -> o.getItem().getName());
//            orderItems =orders.getOrderItems();
            orderItems=orders.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem))
                    .collect(Collectors.toList());
        }
    }


    @Data
    static class  OrderItemDto{

        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem) {
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }
}
