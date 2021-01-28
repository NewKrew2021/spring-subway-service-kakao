package subway.member.domain;

import java.util.Optional;

public class LoginMember {

    private static final Long ID_DEFAULT = 0L;
    private static final String EMAIL_DEFAULT = "email";
    private static final int AGE_DEFAULT = 0;

    private Long id;
    private String email;
    private Integer age;

    private static LoginMember defaultLoginMember;

    static {
        defaultLoginMember = new LoginMember(ID_DEFAULT, EMAIL_DEFAULT, AGE_DEFAULT);
    }

    public LoginMember() {
    }

    public LoginMember(Long id, String email, Integer age) {
        this.id = id;
        this.email = email;
        this.age = age;
    }

    public LoginMember(Member member) {
        this(member.getId(), member.getEmail(), member.getAge());
    }

    public static LoginMember of(Member member) {
        return Optional.ofNullable(member)
                .map(LoginMember::new)
                .orElse(defaultLoginMember);
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
