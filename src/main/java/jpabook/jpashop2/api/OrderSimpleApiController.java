package jpabook.jpashop2.api;

import jpabook.jpashop2.domain.Address;
import jpabook.jpashop2.domain.OrderStatus;
import jpabook.jpashop2.domain.Orders;
import jpabook.jpashop2.repository.OrderRepository;
import jpabook.jpashop2.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;


/*
*   XToOne( ManyToOne , OneToOne)
*   Orders
*   Orders -> Member
*   Orders -> Delivery
*
*/


@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    // 무한 루프에 들어가는데 json 입장에선 서로 양방향연관관계에서 객체를 서로 바라보니 계속 쿼리잡고 따라간다.  @JsonIgnore 사용 필요
    // bytebuddy.ByteBuddyInterceptor 이후에 LAZY 로딩에 의해서 프록시 객체(멍때리는 영속성) 가져와서 에러를 일으킨다.
    @GetMapping("api/v1/simple-orders")
    public List<Orders> orderv1() {
        List<Orders> all = orderRepository.findAll(new OrderSearch());
        for (Orders orders : all) {
            orders.getMember().getUsername();  // lazy 강제 초기화
            orders.getDelivery().getAddress(); // lazy 강제 초기화

        }
        return all;
    }


    @GetMapping("api/v2/simple-orders")
    public List<SimpleOrderDto> orderv2() {

        // 1+N 의 문제가 나온다. LAZY 로딩으로 인한.
        // 1 +  회원 테이블 + 배송 테이블
        List<Orders> all = orderRepository.findAll(new OrderSearch());
        return all.stream().map(o -> new SimpleOrderDto(o)).collect(toList());

    }


    @GetMapping("api/v3/simple-orders")
    public List<SimpleOrderDto> orderv3() {

        List<Orders> all = orderRepository.findAllWithMemberDelivery();
        return all.stream().map(o -> new SimpleOrderDto(o)).collect(toList());

    }
    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Orders orders){
            orderId= orders.getId();
            name = orders.getMember().getUsername();
            orderDate = orders.getOrderDate();
            orderStatus = orders.getStatus();
            address = orders.getDelivery().getAddress();

        }
    }
}


