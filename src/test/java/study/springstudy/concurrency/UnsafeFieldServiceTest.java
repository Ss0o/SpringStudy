package study.springstudy.concurrency;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootTest
public class UnsafeFieldServiceTest {
    @Autowired
    private UnsafeFieldService unsafeFieldService;

    @Test
    void 싱글톤_빈의_공유_필드_문제() throws Exception {
        ExecutorService executorService =
                Executors.newFixedThreadPool(2);

        CountDownLatch startLatch = new CountDownLatch(1);

        try {
            Future<String> firstResult =
                    executorService.submit(() -> {
                        startLatch.await();
                        return unsafeFieldService.process("상수");
                    });
            Future<String> secondResult =
                    executorService.submit(() -> {
                        startLatch.await();
                        return unsafeFieldService.process("철수");
                    });
            startLatch.countDown();

            System.out.println(
                    "상수 요청의 결과: " + firstResult.get()
            );

            System.out.println(
                    "철수 요청의 결과: " + secondResult.get()
            );
        } finally {
            executorService.shutdown();
        }
    }
}
