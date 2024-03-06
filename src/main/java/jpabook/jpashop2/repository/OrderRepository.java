package jpabook.jpashop2.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Orders orders){
        em.persist(orders);
    }

    public Orders findOne(Long id){
        return em.find(Orders.class,id);
    }

   // public List<Orders> findAll(OrderSearch orderSearch){}
}
