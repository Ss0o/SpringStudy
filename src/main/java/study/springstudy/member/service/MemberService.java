package study.springstudy.member.service;

import org.springframework.stereotype.Service;
import study.springstudy.member.domain.Member;
import study.springstudy.member.repository.MemberRepository;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    /*
        Spring
        → MemberRepository 객체 생성
        → MemberService 생성자에 전달
        → MemberService 객체 생성

        MemberService는 MemberRepository에 의존한다.
        Spring이 그 의존 객체를 주입해준다.
     */
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member register(String name, String email) {
        Member member = new Member(name, email);

        return memberRepository.save(member);
    }
    public Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니. id = " + id));
    }
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public Member updateMember(Long id, String name, String email) {
        Member member = findMember(id);

        member.update(name, email);

        return member;
    }

    public void deleteMember(Long id) {
        findMember(id);
        memberRepository.deleteById(id);
    }
}
