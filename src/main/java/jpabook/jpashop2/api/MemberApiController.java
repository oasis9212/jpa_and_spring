package jpabook.jpashop2.api;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;


    @PostMapping("/api/v1/memebers")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id=memberService.join(member);
        return  new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/memebers")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member= new Member();
        // 방어적인 로직 추가. 별도의 객체를 만들어서
        // 엔티티에서 수정이 들어가더라도 컴파일 애러가 나오니깐.
        // api 스펙이 변경이 안된다.
        member.setUsername(request.getName());

        Long id=memberService.join(member);
        return  new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }


    @Data
    static class CreateMemberRequest{
        @NotEmpty
        private String name;

    }
}
