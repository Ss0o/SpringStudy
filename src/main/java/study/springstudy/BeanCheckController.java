package study.springstudy;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.springstudy.member.service.MemberService;

@RestController
public class BeanCheckController {
    private final ApplicationContext applicationContext;

    public BeanCheckController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @GetMapping("/beans/check")
    public String checkBean() {
        MemberService service1 = applicationContext.getBean(MemberService.class);

        MemberService service2 = applicationContext.getBean(MemberService.class);

        return "same instance: " + (service1 == service2);
    }
}
