package study.springstudy.concurrency;

import org.springframework.stereotype.Service;

/*
    "동시에 요청이 들어왔을 때"
    1. unsafeFieldService.process("상수");
    2. unsafeFieldService.process("철수");

    스레드 A: currentUser = "상수"
    스레드 B: currentUser = "철수"

    스레드 A: 100ms 대기
    스레드 B: 100ms 대기

    스레드 A: currentUser 반환 → "철수"
    스레드 B: currentUser 반환 → "철수"

    스레드 A는 "상수"를 넣었지만, 대기하는 동안 스레드 B가 값을 "철수"로 바뀜.
    이처럼 여러 스레드가 공유 자원에 동시에 접근하고 결과가 실행 순서에 따라 달라지는 상황을 Race Condition, 즉 경쟁 상태라 함.
 */

@Service
public class UnsafeFieldService {
    private String currentUser;

    public String process(String user) {
        currentUser = user;

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
