package study.springstudy.member.service;

import org.springframework.stereotype.Service;
import study.springstudy.member.domain.Member;
import study.springstudy.member.exception.DuplicateEmailException;
import study.springstudy.member.exception.MemberNotFoundException;
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
        validateDuplicateEmail(email);
        Member member = new Member(name, email);

        return memberRepository.save(member);
    }

    private void validateDuplicateEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(member -> {
                    throw new DuplicateEmailException(email);
                });
    }
    public Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() ->
                        new MemberNotFoundException(id)
                );
    }
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public Member updateMember(Long id, String name, String email) {
        Member member = findMember(id);

        validateDuplicateEmailForUpdate(id, email);

        member.update(name, email);

        return member;
    }

    private void validateDuplicateEmailForUpdate(Long memberId, String email) {
        memberRepository.findByEmail(email)
                .ifPresent(existingMember -> {
                    if(!existingMember.getId().equals(memberId)) {
                        throw new DuplicateEmailException(email);
                    }
                });
    }

    public void deleteMember(Long id) {
        Member member = findMember(id); //삭제 전에 해당 회원이 실제로 존재하는지 확인하기 위해 호출 없으면 예외 발생
        memberRepository.delete(member);
    }
}
