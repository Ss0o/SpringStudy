package study.springstudy.concurrency;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SafeLocalServiceTest {
    @Autowired
    private SafeLocalService safeLocalService;

    @Test
    void 지역_변수는_요청마다_분리된다() throws Exception {
        ExecutorService executorService =
                Executors.newFixedThreadPool(2);

        CountDownLatch startLatch =
                new CountDownLatch(1);

        try {
            Future<String> firstResult =
                    executorService.submit(() -> {
                        startLatch.await();
                        return safeLocalService.process("상수");
                    });

            Future<String> secondResult =
                    executorService.submit(() -> {
                        startLatch.await();
                        return safeLocalService.process("철수");
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

            assertThat(firstResult.get())
                    .isEqualTo("상수");

            assertThat(secondResult.get())
                    .isEqualTo("철수");
        } finally {
            executorService.shutdown();
        }
    }
}
