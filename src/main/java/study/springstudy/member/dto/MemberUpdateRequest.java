package study.springstudy.member.dto;

public class MemberUpdateRequest {
    private String name;
    private String email;

    public MemberUpdateRequest() {
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
