package study.springstudy.hello;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    /*
        GET /hello 요청
             ↓
        HelloController.hello() 실행
             ↓
        HelloResponse 객체 반환
             ↓
        Spring이 getter로 객체의 값 확인
             ↓
        Jackson이 JSON으로 변환 (Jackson: Spring Web의존성 안에 있는 Json 변환 라이브러리)
             ↓
        클라이언트에 응답
    */
    @GetMapping("/hello")
    public HelloResponse hello(@RequestParam String student) {
        return new HelloResponse("hello spring", student, "Spring Boot");
    }

    @GetMapping("/hello/{student}")
    public HelloResponse helloByPath(@PathVariable String student) {
        return new HelloResponse("hello spring", student, "Spring Boot");
    }
    @PostMapping("/hello")
    public HelloResponse createHello(@RequestBody HelloRequest request) {
        return new HelloResponse("hello" + request.getStudent(),
                                                request.getStudent(),
                                                request.getSubject());
    }
}
