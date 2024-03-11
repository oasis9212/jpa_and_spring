package jpabook.jpashop2.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() ==null){
            em.persist(item);
        }else{
            // 병합은 준영속 상태의 엔티티를 영속 상태로 변경할 때 사용하는 기능이다.
            // 모든 데이터를 바꿔치기 하는 기능
            em.merge(item);
            //Item merge  =  em.merge(item);  // item 객체와   em.merge(item);  다른 것 이다,
            // 그저 새로운 영속성 컨텍스트로 만드는 것이다.
            // 안좋게 상황이 되면 item 객체값에서 null 이 있다면 null 값으로 변한다( 너가 변경할 의도가 없다 한들)
        }
    }

    public  Item findOne(Long id){
        return em.find(Item.class,id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i",Item.class).getResultList();
    }
}
