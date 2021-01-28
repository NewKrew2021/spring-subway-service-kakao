package subway.member.domain;

public class LoginMember {
    private static final String ADMIN = "admin";
    public static final LoginMember NOT_LOGINED = new LoginMember();

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

    public LoginMember(Member member) {
        this(member.getId(), member.getEmail(), member.getAge());
    }

    public boolean isLogined() {
        return !this.equals(NOT_LOGINED);
    }

    public boolean isAdmin() {
        return email.equals(ADMIN);
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
