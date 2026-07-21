package study.springstudy.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;

public class HelloServlet extends HttpServlet {
    private final JsonMapper jsonMapper;

    public HelloServlet(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        String name = request.getParameter("name");

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write("hello servlet " + name);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        ServletHelloRequest helloRequest =
                jsonMapper.readValue(
                        request.getReader(),
                        ServletHelloRequest.class
                );
        ServletHelloResponse helloResponse =
                new ServletHelloResponse((
                        "hello servlet " + helloRequest.name()
                        ));
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        jsonMapper.writeValue(response.getWriter(), helloResponse);
    }

}
