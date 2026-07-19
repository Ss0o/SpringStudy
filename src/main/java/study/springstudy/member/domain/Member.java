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


    /*
        왜 update 기능을 repository에 추가하지 않고 Member클래스에 작생했는가?
          "역할의 분리"
            Service
            → 수정할 회원을 찾도록 Repository에 요청
            Repository
            → 저장소에서 회원 객체 조회
            Service
            → 조회된 회원에게 정보 변경을 요청
            Member
            → 자신의 name, email 변경
     */
    public void update(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
