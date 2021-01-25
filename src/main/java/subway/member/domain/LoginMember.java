package subway.member.domain;

public class LoginMember {
    public static final int CHILD_MIN_AGE = 6;
    public static final int TEENAGER_MIN_AGE = 13;
    public static final int TEENAGER_MAX_AGE = 19;
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
        return isLoginMember() && CHILD_MIN_AGE <= age && age < TEENAGER_MIN_AGE;
    }

    public boolean isTeenager() {
        return isLoginMember() && TEENAGER_MIN_AGE <= age && age < TEENAGER_MAX_AGE;
    }
}
