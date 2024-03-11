package jpabook.jpashop2.service;


import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.Item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemupdateTest {
    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception{


        // 변경 감지 == dirty checking
        // flush 할때 일어나
    }

}
