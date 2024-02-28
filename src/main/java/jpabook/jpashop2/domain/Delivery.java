package jpabook.jpashop2.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;
    @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY)
    private Orders order;
    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //   ORDINAL 은 쓰지말자.  @Enumerated 써라. 이넘 타입일땐.
    private DeliveryStatus status;



}
