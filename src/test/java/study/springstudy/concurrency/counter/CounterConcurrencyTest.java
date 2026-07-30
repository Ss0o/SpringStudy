package study.springstudy.concurrency.counter;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.IntSupplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/*
    Spring에서 synchronized만 쓰면 모든 문제가 해결될까?

    아님. synchronized는 현재 JVM 안의 스레드들만 제어함.

    서버를 두 대 실행하면 각각 다른 객체와 잠금을 가지고 있다.

    예시:
    서버 A JVM
    └→ synchronized 잠금 A

    서버 B JVM
    └→ synchronized 잠금 B

    서버 A와 서버 B는 서로의 잠금을 알지 못함.
    따라서 DB 잔액, 재고, 포인트처럼 여러 서버가 공유하는 데이터는
    DB 트랜잭션
    낙관적 락
    비관적 락
    원자적 UPDATE
    Redis 분산 락 등을 사용해야 함.
 */
public class CounterConcurrencyTest {
    private static final int THREAD_COUNT = 10;
    private static final int INCREMENT_COUNT = 100_000;

    @Test
    void 일반_int는_증가값이_유실될_수_있다()
        throws InterruptedException {
        UnsafeCounter counter = new UnsafeCounter();

        int actual = runConcurrentIncrement(
                counter::increment,
                counter::getCount
        );
        int expected = THREAD_COUNT * INCREMENT_COUNT;

        System.out.println("예상 결과: " + expected);
        System.out.println("실제 결과: " + actual);

        assertThat(actual)
                .isLessThanOrEqualTo(expected);
    }

    @Test
    void synchronized는_증가값을_보호한다()
        throws InterruptedException {
        SynchronizedCounter counter =
                new SynchronizedCounter();

        int actual = runConcurrentIncrement(
                counter::increment,
                counter::getCount
        );
        int expected = THREAD_COUNT * INCREMENT_COUNT;

        System.out.println("예상 결과: " + expected);
        System.out.println("synchronized 결과: " + actual);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void AtomicInteger는_증가값을_보호한다()
        throws InterruptedException {
        AtomicCounter counter = new AtomicCounter();

        int actual = runConcurrentIncrement(
                counter::increment,
                counter::getCount
        );

        int expected = THREAD_COUNT * INCREMENT_COUNT;

        System.out.println("예상 결과: " + expected);
        System.out.println("AtomicInteger 결과: " + actual);

        assertThat(actual)
                .isEqualTo(expected);
    }


    private int runConcurrentIncrement(
            Runnable increment,
            IntSupplier countSupplier
    ) throws InterruptedException {

        ExecutorService executorService =
                Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch startLatch =
                new CountDownLatch(1);
        CountDownLatch endLatch =
                new CountDownLatch(THREAD_COUNT);

        try {
            for (int i = 0; i < THREAD_COUNT; i++) {
                executorService.submit(() -> {
                    try {
                        startLatch.await();

                        for (int j = 0; j < INCREMENT_COUNT; j++) {
                            increment.run();
                        }
                    } catch (InterruptedException exception) {
                        Thread.currentThread().interrupt();
                    } finally {
                        endLatch.countDown();;
                    }
                });
            }
            startLatch.countDown();

            boolean completed =
                    endLatch.await(10, TimeUnit.SECONDS);

            if (!completed) {
                throw new IllegalStateException(
                        "테스트 작업이 제한 시간 안에 끝나지 않았습니다."
                );
            }

            return countSupplier.getAsInt();
        } finally {
            executorService.shutdownNow();
        }
    }
}
