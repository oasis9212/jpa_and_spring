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


    public List<Orders> findAllWithItem() {

        // jpa 자체적으로  distinct  Orders 가 같아지면 자동으로 변환해준다.
        // 1 대 다를 패치 조인하는 순간 패이징 처리가 불가능하자.
        // firstset / lastset 하는 순간 메모리 자체에서 정렬시킨다.  -> 메모리 뻩을 가능성 높다.
        return  em.createQuery(
                "select distinct o from Orders o " +
                        " join fetch o.member m " +
                        " join fetch o.delivery d  " +
                        " join fetch o.orderItems oi " +
                        " join fetch oi.item i", Orders.class
                ).getResultList();
    }
}
