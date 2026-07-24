package study.springstudy.pattern.template;

public abstract class AbstractTimeTemplate {
    public final String execute() {
        long startTime = System.nanoTime();

        try {
            return call();
        } finally {
            long endTime = System.nanoTime();
            long executionTime =
                    (endTime - startTime) / 1_000_000;

            System.out.println(
                    "실행 시간: " + executionTime + "ms"
            );
        }

    }
    protected abstract String call();
}
