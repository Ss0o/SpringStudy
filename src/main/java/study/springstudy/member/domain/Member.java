package study.springstudy.member.domain;

public class Member {
    private Long id;
    private String name;
    private String email;

    public Member(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void assignId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
