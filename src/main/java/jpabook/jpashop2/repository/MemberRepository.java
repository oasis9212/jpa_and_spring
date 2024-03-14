package jpabook.jpashop2.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;


    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> membersfindAll(){
        return em.createQuery("select m from Member m",Member.class).getResultList();
    }


    public List<Member> findByname(String name){
        return em.createQuery("select m from Member m where m.username= :name",Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
