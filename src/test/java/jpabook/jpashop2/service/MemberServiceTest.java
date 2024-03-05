package jpabook.jpashop2.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import jpabook.jpashop2.MemberRepository;
import jpabook.jpashop2.domain.Member;
import org.junit.Assert;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(false)
    public void 회원가입() throws Exception{
        //
        Member member= new Member();
        member.setUsername("kim");
        //
        Long memeber=memberService.join(member);
        //
        assertEquals(member, memberRepository.find(memeber));
    }

    @Test
    public void  중복() throws  Exception{

        Member member1= new Member();
        member1.setUsername("kim1");

        Member member2= new Member();
        member2.setUsername("kim1");


        memberService.join(member1);
        memberService.join(member2);

        fail("예ㅚ가 발생해야한다.");

    }
}