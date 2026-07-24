package study.springstudy.pattern.proxy;

public class CacheProxy implements Subject{

    private final Subject target;
    private String cacheValue;

    public CacheProxy(Subject target) {
        this.target = target;
    }

    @Override
    public String operation() {
        if (cacheValue == null) {
            System.out.println("캐시 없음: 실제 객체 호출");

            cacheValue = target.operation();
        } else {
            System.out.println("캐시 있음: 저장된 결과 반환");
        }
        return cacheValue;
    }
}
