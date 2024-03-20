package jpabook.jpashop2.repository;

import jpabook.jpashop2.domain.Address;
import jpabook.jpashop2.domain.OrderStatus;
import jpabook.jpashop2.domain.Orders;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderSimpleQueryDto{
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto(Orders orders){
        orderId= orders.getId();
        name = orders.getMember().getUsername();
        orderDate = orders.getOrderDate();
        orderStatus = orders.getStatus();
        address = orders.getDelivery().getAddress();

    }
}
