package study.springstudy.member.controller;

import org.springframework.web.bind.annotation.*;
import study.springstudy.member.domain.Member;
import study.springstudy.member.dto.MemberCreateRequest;
import study.springstudy.member.dto.MemberResponse;
import study.springstudy.member.service.MemberService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public MemberResponse createMember(@RequestBody MemberCreateRequest request) {
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

    public MemberResponse toResponse(Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getEmail());
    }

}
