package jpabook.jpashop2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest

class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Transactional
    @Test
    public  void testMember() throws  Exception{
        // give
        Member member= new Member();
        member.setUsername("memberA");

        // when
        Long saveId = memberRepository.save(member);
        Member findmember = memberRepository.find(saveId);
        // then
        Assertions.assertThat(findmember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findmember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findmember).isEqualTo(member);

    }
}