package study.springstudy.concurrency.threadlocal;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ThreadLocalServiceTest {
    private final ThreadLocalService threadLocalService =
            new ThreadLocalService();

    @Test
    void 스레드마다_서로_다른_값을_저장한다() throws Exception {
        ExecutorService executorService =
                Executors.newFixedThreadPool(2);

        CountDownLatch startLatch =
                new CountDownLatch(1);

        try {
            Future<String> firstResult =
                    executorService.submit(() -> {
                        startLatch.await();

                        return threadLocalService.process("상수");
                    });

            Future<String> secondResult =
                    executorService.submit(() -> {
                        startLatch.await();

                        return threadLocalService.process("철수");
                    });

            startLatch.countDown();

            String firstResponse = firstResult.get();
            String secondResponse = secondResult.get();

            System.out.println(
                    "상수 요청의 결과: " + firstResponse
            );

            System.out.println(
                    "철수 요청의 결과: " + secondResponse
            );

            assertThat(firstResponse)
                    .isEqualTo("상수");

            assertThat(secondResponse)
                    .isEqualTo("철수");
        } finally {
            executorService.shutdownNow();
        }

    }
    @Test
    void remove하지_않으면_다음_작업에_값이_남는다()
            throws Exception {

        ExecutorService executorService =
                Executors.newSingleThreadExecutor();

        try {
            Future<String> firstRequest =
                    executorService.submit(() ->
                            threadLocalService
                                    .processWithoutClear("상수")
                    );

            String firstResponse = firstRequest.get();

            Future<String> secondRequest =
                    executorService.submit(
                            threadLocalService::getCurrentUser
                    );

            String secondResponse = secondRequest.get();

            System.out.println(
                    "첫 번째 요청 결과: " + firstResponse
            );

            System.out.println(
                    "두 번째 요청이 발견한 기존 값: "
                            + secondResponse
            );

            assertThat(secondResponse)
                    .isEqualTo("상수");
        } finally {
            executorService.submit(
                    threadLocalService::clear
            ).get();

            executorService.shutdownNow();
        }
    }
}
