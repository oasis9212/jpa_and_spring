package jpabook.jpashop2.service;

import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
 //@AllArgsConstructor // 생성자 자동 생성
@RequiredArgsConstructor // final 가지고 있는 변수들로만 생성자 생성
public class MemberService {


    private final MemberRepository memberRepository;


    //따로 설정하면 이 매서드가 우선권을 갖는다.
    @Transactional
    public  Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // 중복된 이름이 있다면 애러 표시
        List<Member> list= memberRepository.findByname(member.getUsername());

        if(list.size()>0){
            throw  new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public  List<Member> findmemeber(){
        return memberRepository.membersfindAll();
    }

    public  Member findOne(Long memberID){
        return memberRepository.find(memberID);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member=memberRepository.find(id);
        member.setUsername(name);
    }
}
