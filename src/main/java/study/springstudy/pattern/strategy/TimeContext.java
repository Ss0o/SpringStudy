package study.springstudy.pattern.strategy;

public class TimeContext {
    private final Strategy strategy;

    public TimeContext(Strategy strategy) {
        this.strategy = strategy;
    }
    public String execute() {
        long startTime = System.nanoTime();

        try {
            return strategy.call();
        } finally {
            long endTime = System.nanoTime();

            long executionTime =
                    (endTime - startTime) / 1_000_000;

            System.out.println(
                    "전략 실행 시간: "
                    + executionTime
                    + "ms"
            );
        }
    }
}
