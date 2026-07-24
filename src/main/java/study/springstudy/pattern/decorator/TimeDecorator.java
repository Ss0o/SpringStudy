package study.springstudy.pattern.decorator;

public class TimeDecorator implements Component{

    private final Component target;

    public TimeDecorator(Component target) {
        this.target = target;
    }

    @Override
    public String operation() {
        long startTime = System.nanoTime();

        try {
            return target.operation();
        } finally {
            long endTime = System.nanoTime();

            long executionTime = (endTime - startTime) / 1_000_000;

            System.out.println("실행 시간" + executionTime + "ms");
        }
    }
}
