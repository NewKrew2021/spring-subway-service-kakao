package subway.member.domain;

public class LoginMember {
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

    public boolean isLoginMember() {
        return id >= 1;
    }

    public boolean isChild() {
        return isLoginMember() && 6 <= age && age < 13;
    }

    public boolean isTeenager() {
        return isLoginMember() && 13 <= age && age < 19;
    }
}
