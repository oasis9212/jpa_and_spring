package jpabook.jpashop2.controller;

import jakarta.validation.Valid;
import jpabook.jpashop2.domain.Address;
import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm());
        return "members/createMemberFrom";
    }
    // BindingResult 오류가 있다면 여기에 담겨서 실행
    @PostMapping("/members/new")
    public String registerMember(@Valid MemberForm memberForm, BindingResult result){
        if(result.hasErrors()){
            return "members/createMemberFrom";
        }

        Address address=  new Address(memberForm.getCity(),memberForm.getStreet(),memberForm.getZipcode());
        Member member= new Member();
        member.setUsername(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";

    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members= memberService.findmemeber();
        model.addAttribute("members",members);
        return "members/memberList";

    }
}
