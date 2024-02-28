package jpabook.jpashop2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter

// setter의 호출은 데이터 변환 의문제 가 생길수도 있다.
// setter 를 무지성으로 만들면 추후 디비 데이터가 어떻게 변경 추적이 어려울 가능성이 있다.
// 로직별로 제공하는 방안이 좋다.
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String username;
    @Embedded
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Orders> orders= new ArrayList<>();

    public void addorders(Orders orders){
        this.orders.add(orders);
        orders.setMember(this);
    }

}
