package study.springstudy.concurrency;

import org.springframework.stereotype.Service;

/*
    UnsafeFieldService에서 바뀐 부분
    인스턴스 필드 방식(private String currentUser;)
    에서
    지역 변수 방식(메서드 내에서 String currentUser = user;)
    으로 바꿈으로써
    서로의 지역 변수를 덮어쓰지 않음.

    하지만,
    지역 변수라는 이유만으로 모든 상황에서 무조건 안전한 것은 아니다.
    다음처럼 지역 변수가 공유 객체를 가리킬 수도 있음.

    private final List<String> users =
        new ArrayList<>();

    public void process(String user) {
        List<String> localUsers = users;
        localUsers.add(user);
    }

    localUsers 변수 자체는 각 스레드에 따로 있지만, 두 변수가 가리키는 객체는 같은 users다.

    스레드 A의 localUsers ─┐
                           ├→ 같은 ArrayList
    스레드 B의 localUsers ─┘

    >> 따라서 정확한 기준
    1. 각 스레드만 사용하는 값
        → 비교적 안전
    2. 여러 스레드가 같은 변경 가능한 객체를 공유
        → 동시성 문제 가능

 */

//Spring의 싱글톤 Controller와 Service는 가급적 상태를 저장하지 않는 stateless 구조로 작성한다.
@Service
public class SafeLocalService {
    public String process(String user) {
        String currentUser = user;

        sleep(100);

        return currentUser;
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(
                    "스레드 대기 중 인터럽트가 발생했습니다.",
                    exception
            );
        }
    }
}
