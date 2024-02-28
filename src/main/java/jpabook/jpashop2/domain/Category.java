package jpabook.jpashop2.domain;

import jakarta.persistence.*;
import jpabook.jpashop2.domain.Item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "category_item",
           joinColumns =  @JoinColumn(name = "category_id") ,
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items= new ArrayList<>();


    // 자기 자신이 부모 자식일 경우   예) 대중소 같은거 있을때. 셀프 양반향관계.
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child= new ArrayList<>();



    public void addcategory(Category category){
        this.child.add(category);
        category.setParent(this);
    }
}
