package jpabook.jpashop2.api;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("api/v1/members")
    public List<Member> memberV1(){
        // 엔티티를 직접 외부에 노출하면 안된다.
        // 기본적으로 엔티티값 노출된다 (예: password)
        // api 의 용도가 다양하게 만드는데  각각 대응은 어렵다.
        // 유연성이 떻어진다.
        return memberService.findmemeber();
    }

    @GetMapping("api/v2/memebers")
    public Result memberV2(){
        List<Member> members= memberService.findmemeber();
        List<MemeberDto> collect = members.stream().map(m -> new MemeberDto(m.getUsername())).collect(Collectors.toList());
        return new Result(members.size(),collect);
    }


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


    @PutMapping("/api/v2/memebers/{id}")
    public  UpdateMemeberResponse UpdateMemberV2(@PathVariable("id")Long id, @RequestBody @Valid UpdateMemberReq req){

        memberService.update(id, req.getName());
       Member m= memberService.findOne(id);

        return  new UpdateMemeberResponse(m.getId(), m.getUsername());
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
    @Data
    static class UpdateMemberReq{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemeberResponse{
        private Long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class  Result<T>{
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static  class MemeberDto{
        private String name;
    }

}
