package study.springstudy.pattern.callback;

public class TimeLogTemplate {
    public String execute(Callback callback) {
        long startTime = System.nanoTime();

        try {
            return callback.call();
        } finally {
            long endTime = System.nanoTime();

            long executeTime = (endTime - startTime) / 1_000_000;

            System.out.println("콜백 실행 시간: " + executeTime + "ms");
        }
    }
}
