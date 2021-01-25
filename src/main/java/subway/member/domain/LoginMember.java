package subway.member.domain;

public class LoginMember {
    public static final LoginMember NO_ONE = new LoginMember();
    private Long id;
    private String email;
    private Integer age;

    public LoginMember() {
    }

    public LoginMember(Long id, String email, Integer age) {
        this.id = id;
        this.email = email;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    public boolean isNotLoggedIn() {
        return this.equals(NO_ONE);
    }
}
