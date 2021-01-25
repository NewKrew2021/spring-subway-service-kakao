package subway.member.domain;

public class LoginMember {
    private static final int CHLID_AGE_START = 6;
    private static final int TEENAGER_AGE_START = 13;
    private static final int TEENAGER_AGE_END = 19;
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
        return isLoginMember() && CHLID_AGE_START <= age && age < TEENAGER_AGE_START;
    }

    public boolean isTeenager() {
        return isLoginMember() && TEENAGER_AGE_START <= age && age < TEENAGER_AGE_END;
    }
}
