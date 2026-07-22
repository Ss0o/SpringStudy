package study.springstudy.member.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import study.springstudy.member.domain.Member;
import study.springstudy.member.dto.MemberCreateRequest;
import study.springstudy.member.dto.MemberResponse;
import study.springstudy.member.dto.MemberUpdateRequest;
import study.springstudy.member.service.MemberService;

import java.util.ArrayList;
import java.util.List;

/*
    Spring 애플리케이션은
    Controller·Service·Repository로 책임을 나눌 수 있다.

    각 계층에는 여러 Bean 클래스가 존재할 수 있다.

    Spring Bean은 기본적으로
    클래스별로 하나의 객체가 생성되어 공유된다.
 */

/*
    @Component
    ├── @Controller
    │   └── @RestController
    ├── @Service
    ├── @Repository
    └── @ControllerAdvice
        └── @RestControllerAdvice
 */
@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public MemberResponse createMember(@Valid @RequestBody MemberCreateRequest request) {
        Member member = memberService.register(request.getName(), request.getEmail());

        return toResponse(member);
    }
    @GetMapping("/{id}")
    public MemberResponse findMember(@PathVariable Long id) {
        Member member = memberService.findMember(id);

        return toResponse(member);
    }
    @GetMapping()
    public List<MemberResponse> findAllMember() {
        List<Member> members = memberService.findAllMembers();
        List<MemberResponse> responses = new ArrayList<>();

        for(Member m : members) {
            responses.add(toResponse(m));
        }
        return responses;
    }

    @PutMapping("/{id}")
    public MemberResponse updateMember(@PathVariable Long id, @Valid @RequestBody MemberUpdateRequest request) {
        Member member = memberService.updateMember(id, request.getName(), request.getEmail());
        return toResponse(member);
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }


    public MemberResponse toResponse(Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getEmail());
    }

}
