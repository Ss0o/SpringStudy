package study.springstudy.pattern.proxy;

public class RealSubject implements Subject{

    @Override
    public String operation() {
        System.out.println("실제 객체의 무거운 로직 실행");

        try {
            Thread.sleep(1000); // 실행 시간이 오래 걸리는 로직을 흉내 내기 위한 대체 코드(db 조회, 외부 api호출, 복잡한 계산)
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(exception);
        }

        return "실제 조회 결과";
    }
}
