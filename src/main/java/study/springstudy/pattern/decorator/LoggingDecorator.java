package study.springstudy.pattern.decorator;

public class LoggingDecorator implements Component{

    private final Component target;

    public LoggingDecorator(Component target) {
        this.target = target;
    }

    @Override
    public String operation() {
        System.out.println("호출 시작");

        String result = target.operation();

        System.out.println("호출 결과: " + result);
        System.out.println("호출 종료");

        return result;
    }
}
