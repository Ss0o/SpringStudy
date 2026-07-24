package study.springstudy.pattern.strategy;

public class MemberSaveStrategy implements Strategy{

    @Override
    public String call() {
        return "회원 저장 전략 실행";
    }
}
