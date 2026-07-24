package study.springstudy.pattern.strategy;

public class MemberFindStrategy implements Strategy{

    @Override
    public String call() {
        return "회원 조회 전략 실행";
    }
}
