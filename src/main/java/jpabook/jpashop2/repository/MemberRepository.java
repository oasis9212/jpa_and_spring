package jpabook.jpashop2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop2.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;


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
        return em.createQuery("select m from Member m where m.name= :name",Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
