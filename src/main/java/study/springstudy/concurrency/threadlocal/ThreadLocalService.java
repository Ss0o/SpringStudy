package study.springstudy.concurrency.threadlocal;

import org.springframework.stereotype.Service;

@Service
public class ThreadLocalService {
    public String process(String user) {
        UserContextHolder.setUser(user);

        try {
            sleep(100);

            return UserContextHolder.getUser();
        } finally {
            UserContextHolder.clear();
        }
    }

    public String processWithoutClear(String user) {
        UserContextHolder.setUser(user);

        return UserContextHolder.getUser();
    }

    public String getCurrentUser() {
        return UserContextHolder.getUser();
    }

    public void clear() {
        UserContextHolder.clear();
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
