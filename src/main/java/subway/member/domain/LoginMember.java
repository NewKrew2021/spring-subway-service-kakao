package subway.member.domain;

import subway.exception.custom.AuthorizationException;

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

    public static LoginMember from(Member member) {
        return new LoginMember(member.getId(), member.getEmail(), member.getAge());
    }

    private void validate() {
        if (id == null) {
            throw new AuthorizationException();
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
