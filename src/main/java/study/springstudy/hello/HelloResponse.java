package study.springstudy.hello;

public class HelloResponse {
    private String message;
    private String student;
    private String subject;

    public HelloResponse(String message, String student, String subject) {
        this.message = message;
        this.student = student;
        this.subject = subject;
    }
    public String getMessage() {
        return message;
    }
    public String getStudent() {
        return student;
    }
    public String getSubject() {
        return subject;
    }
}
