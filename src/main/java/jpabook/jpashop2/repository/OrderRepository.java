package jpabook.jpashop2.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import jpabook.jpashop2.domain.Orders;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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


    // 동적 쿼리를 위한 queryDSL 이란것이 있다.
    public List<Orders> findAll(OrderSearch orderSearch){
        String jpql = "select o From Orders o join o.member m";
        boolean isFirstCondition = true;
//주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }
//회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.username like :name";
        }
        TypedQuery<Orders> query = em.createQuery(jpql, Orders.class)
                .setMaxResults(1000); //최대 1000건
        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();

    }

    public List<Orders> findAllWithMemberDelivery() {
       return em.createQuery(
                "select o from Orders o "+
                        " join fetch o.member m " +
                        " join fetch o.delivery d", Orders.class
        ).getResultList();

    }

    public List<OrderSimpleQueryDto> findOrderDtos() {
       return   em.createQuery("select o from Orders o " +
                         " join o.member m  " +
                         " join o.delivery d ", OrderSimpleQueryDto.class ).getResultList();
    }




}
