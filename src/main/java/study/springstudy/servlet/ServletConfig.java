package study.springstudy.servlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.json.JsonMapper;

@Configuration
public class ServletConfig {
    @Bean
    public ServletRegistrationBean<HelloServlet> helloServlet(JsonMapper jsonMapper) {
        return new ServletRegistrationBean<>(
                new HelloServlet(jsonMapper),
                "/servlet/hello"
        );
    }
}
