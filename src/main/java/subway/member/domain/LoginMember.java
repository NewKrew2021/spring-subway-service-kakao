package subway.member.domain;

import subway.path.domain.Person;

public class LoginMember {
    public static final LoginMember guestMember = new LoginMember(0L, "guest", Person.ADULT.getMinAge());
    private final Long id;
    private final String email;
    private final Integer age;

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
}
