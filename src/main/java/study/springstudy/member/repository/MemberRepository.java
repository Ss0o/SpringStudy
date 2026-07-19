package study.springstudy.member.repository;

import org.springframework.stereotype.Repository;
import study.springstudy.member.domain.Member;

import java.util.*;

@Repository
public class MemberRepository {
    //db를 사용하지 않고 메모리로 저장하는 방식
    private final Map<Long, Member> store = new HashMap<>();
    private long sequence = 0L;

    public Member save(Member member) {
        Long id = ++sequence;

        member.assignId(id);
        store.put(id, member);

        return member;
    }

    public Optional<Member> findById(Long id) {
        // 존재하지 않는 키를 조회하면 null
        return Optional.ofNullable(store.get(id));
    }
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void delete(Member member) {
        store.remove(member.getId());
    }

    public Optional<Member> findByEmail(String email) {
        return store.values()
                .stream()
                .filter(member -> member.getEmail().equals(email))
                .findFirst();
    }

}
