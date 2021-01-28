package subway.member.domain;

import subway.auth.exception.AuthorizationException;

public class LoginMember {

    private static final String NEED_LOGIN_MESSAGE = "로그인이 필요합니다.";

    private Long id;
    private String email;
    private Integer age;
    private Boolean loginStatus;

    public LoginMember() {
    }

    public LoginMember(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public LoginMember(Long id, String email, Integer age, boolean loginStatus) {
        this.id = id;
        this.email = email;
        this.age = age;
        this.loginStatus = loginStatus;
    }

    public static LoginMember notLogin() {
        return new LoginMember(false);
    }

    public static LoginMember of(Member member) {
        return new LoginMember(member.getId(), member.getEmail(), member.getAge(), true);
    }

    public Long getId() {
        validateLogin();
        return id;
    }

    public String getEmail() {
        validateLogin();
        return email;
    }

    public Integer getAge() {
        validateLogin();
        return age;
    }

    private void validateLogin() {
        if (!loginStatus) {
            throw new AuthorizationException(NEED_LOGIN_MESSAGE);
        }
    }
}
