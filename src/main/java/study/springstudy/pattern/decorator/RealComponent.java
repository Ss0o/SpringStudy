package study.springstudy.pattern.decorator;

public class RealComponent implements Component{

    @Override
    public String operation() {
        return "회원 조회 결과";
    }
}
