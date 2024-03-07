package jpabook.jpashop2.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.Address;
import jpabook.jpashop2.domain.Item.Book;
import jpabook.jpashop2.domain.Item.Item;
import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.domain.OrderStatus;
import jpabook.jpashop2.domain.Orders;
import jpabook.jpashop2.exception.NotEnoughStockException;
import jpabook.jpashop2.repository.OrderRepository;
import org.junit.Assert;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OrderServiceTest {


    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository ordersRepository;

    @Test
    public void 상품주문() throws  Exception{
        Member member=createMember("회원 1", new Address("서울", "강", "123-333"));
        Book book=createBook("avb",10000,10);

        Long orderId= orderService.Order(member.getId(), book.getId(), 2 );

        Orders getorder= ordersRepository.findOne(orderId);

        Assert.assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getorder.getStatus());
        Assert.assertEquals("주문한 상품 종류수가 정확해야한다. ",1,getorder.getOrderItems().size());
        Assert.assertEquals("주문 가격  가격 *수량",10000* 2, getorder.gettotalPrice());
        Assert.assertEquals("주문 한 만큼 재고가 줄어야한다.",8, book.getStockQuantity());
    }

    @Test
    public void 주문취소() throws Exception{
        Member member=createMember("회원 1", new Address("서울", "강", "123-333"));
        Item item=createBook("avb",10000,10);

        int ordercount =2;

        Long orderId= orderService.Order(member.getId(),item.getId(),ordercount);

        orderService.cancelOrder(orderId);

        Orders getorder= ordersRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 cancel 이다",OrderStatus.CANCEL,getorder.getStatus());
        assertEquals("주문 취소했으니 재고는 그대로다",10,item.getStockQuantity());
    }

    @Test
    public  void 상품주문_재고초과() throws Exception{
        Member member=createMember("회원 1", new Address("서울", "강", "123-333"));
        Item item=createBook("avb",10000,10);

        int orderCount=11;

        orderService.Order(member.getId(),item.getId(),orderCount);

        fail("여기까지 오면안돼");
    }


    private Book createBook(String name, int price, int stockQuantity) {
        Book book= new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember(String username, Address address) {
        Member member= new Member();
        member.setUsername(username);
        member.setAddress(address);
        em.persist(member);
        return  member;
    }
}