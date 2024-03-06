package jpabook.jpashop2.service;

import jpabook.jpashop2.MemberRepository;
import jpabook.jpashop2.domain.Delivery;
import jpabook.jpashop2.domain.Item.Item;
import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.domain.OrderItem;
import jpabook.jpashop2.domain.Orders;
import jpabook.jpashop2.repository.ItemRepository;
import jpabook.jpashop2.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    @Transactional
    public Long Order(Long memberID, Long itemID, int count){

        // 엔티티 조회
        Member member= memberRepository.find(memberID);
        Item item=itemRepository.findOne(itemID);

        // 배송 정보 생성
        Delivery delivery= new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성
        OrderItem orderItem=OrderItem.createOrderItem(item,item.getPrice(), count);


        Orders orders=  Orders.createOrder(member, delivery, orderItem);
        // new Orders() 방식으로 하면 유지보수가 어려우니
        // 코드를 제약 시키는 것이 좋다.
        orderRepository.save(orders);
        return orders.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId){
        Orders orders=  orderRepository.findOne(orderId);
        // 주문 취소
        // 엔티티에 핵심 로직을 넣는 방식이
        // 도메인 모델 패턴 이라고 한다.

        // 내가 맨날했던것들이 트랜젝션 스크립트 패턴이라고 한다.
        // 마이바티스 사용했을때.
        orders.cancel();
    }


//    public List<Orders> findOrder(OrderSearch orderSearch){
//        return
//    }
}
