package jpabook.jpashop2.domain.Item;

import jakarta.persistence.*;
import jpabook.jpashop2.domain.Category;
import jpabook.jpashop2.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")  // 싱글테이블일때 해당하는 타입이 뭐냐?
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items",fetch = FetchType.LAZY)
    private List<Category> categories=  new ArrayList<>();

    // 재고 수량 증가
    public void addstock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removestock(int quantity){
        int reststock= this.stockQuantity - quantity;
         if(reststock<0){
             throw  new NotEnoughStockException("재고가 부족합니다.");
         }
         this.stockQuantity = reststock;
    }

}
