package study.springstudy.pattern.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.springstudy.pattern.callback.TimeLogTemplate;
import study.springstudy.pattern.decorator.Component;
import study.springstudy.pattern.decorator.LoggingDecorator;
import study.springstudy.pattern.decorator.RealComponent;
import study.springstudy.pattern.decorator.TimeDecorator;
import study.springstudy.pattern.proxy.CacheProxy;
import study.springstudy.pattern.proxy.RealSubject;
import study.springstudy.pattern.proxy.Subject;
import study.springstudy.pattern.strategy.MemberFindStrategy;
import study.springstudy.pattern.strategy.MemberSaveStrategy;
import study.springstudy.pattern.strategy.Strategy;
import study.springstudy.pattern.strategy.TimeContext;
import study.springstudy.pattern.template.AbstractTimeTemplate;

@RestController
@RequestMapping("/patterns")
public class PatternController {

    @GetMapping("/template")
    public String templateMethod() {
        AbstractTimeTemplate template =
                new AbstractTimeTemplate() {
                    @Override
                    protected String call() {
                        return "회원 조회 로직 실행";
                    }
                };
        return template.execute();
    }

    @GetMapping("/strategy")
    public String strategy() {
        Strategy strategy = new MemberFindStrategy();

        TimeContext context = new TimeContext(strategy);

        return context.execute();
    }

    @GetMapping("/strategy/save")
    public String saveStrategy() {
        Strategy strategy = new MemberSaveStrategy();

        TimeContext context = new TimeContext(strategy);

        return context.execute();
    }

    @GetMapping("/callback")
    public String callback() {
        TimeLogTemplate template = new TimeLogTemplate();

        return template.execute(
                () -> "회원 조회 콜백 실행"
        );
    }

    @GetMapping("/callback/save")
    public String saveCallback() {
        TimeLogTemplate template =
                new TimeLogTemplate();

        return template.execute(
                () -> "회원 저장 콜백 실행"
        );
    }

    @GetMapping("/proxy")
    public String proxy() {
        Subject realSubject = new RealSubject();
        Subject proxy = new CacheProxy(realSubject);

        String firstResult = proxy.operation();
        String secondResult = proxy.operation();

        return firstResult + " / " + secondResult;
    }

    @GetMapping("/decorator")
    public String decorator() {
        Component component = new RealComponent();
        component = new LoggingDecorator(component);
        component = new TimeDecorator(component);

        return component.operation();

    }
}
