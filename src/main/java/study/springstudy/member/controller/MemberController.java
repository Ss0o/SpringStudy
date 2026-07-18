package study.springstudy.member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.springstudy.member.domain.Member;
import study.springstudy.member.dto.MemberCreateRequest;
import study.springstudy.member.dto.MemberResponse;
import study.springstudy.member.service.MemberService;

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

        return new MemberResponse(member.getId(), member.getName(), member.getEmail());
    }
}
