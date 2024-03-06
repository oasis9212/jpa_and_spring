package jpabook.jpashop2.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems= new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void addmember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }
    public void addorderItems(OrderItem orderitem){
        this.orderItems.add(orderitem);
        orderitem.setOrder(this);
    }
    public void adddelivery(Delivery delivery){
        this.delivery=delivery;
        delivery.setOrder(this);
    }


    // 생성 매서드 create
    public static Orders createOrder(Member member,Delivery delivery, OrderItem... orderItems){
        Orders order = new Orders();
        order.setMember(member);
        order.setDelivery(delivery);
        for( OrderItem orderItem: orderItems){
            order.addorderItems(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // 주문 취소
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw  new IllegalStateException("이미 배송된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem: this.orderItems){
            orderItem.cancel();
        }
    }

    // 전체 주문 가격 조회
    public int gettotalPrice(){
        return orderItems.stream().mapToInt(OrderItem:: getOrderPrice).sum();
    }

}






