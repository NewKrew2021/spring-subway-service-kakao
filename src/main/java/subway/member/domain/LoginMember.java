package subway.member.domain;

public class LoginMember {
    public static final LoginMember NOT_LOGINED = new LoginMember();

    private Long id;
    private String email;
    private Age age;

    public LoginMember() {
    }

    public LoginMember(Long id, String email, Age age) {
        this.id = id;
        this.email = email;
        this.age = age;
    }

    public boolean isLoggedout() {
        return this.equals(NOT_LOGINED);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age.getAge();
    }
}
