package subway.member.domain;

import subway.exception.LoginFailException;

public class LoginMember {
    private Long id;
    private String email;
    private int age;

    public LoginMember() {
    }

    public LoginMember(Long id, String email, int age) {
        this.id = id;
        this.email = email;
        this.age = age;
    }

    private void validate() {
        if(id == null){
            throw new LoginFailException("id가 null입니다.");
        }
    }

    public Long getId() {
        validate();
        return id;
    }

    public String getEmail() {
        validate();
        return email;
    }

    public int getAge() {
        return age;
    }
}
