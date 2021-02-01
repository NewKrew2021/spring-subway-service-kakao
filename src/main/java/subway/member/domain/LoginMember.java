package subway.member.domain;

public class LoginMember {
    public static final LoginMember GUEST = new LoginMember(-1L,"GUEST",1000);
    private LoginMemberType type;
    private Long id;
    private String email;
    private Integer age;

    public LoginMember() {
    }

    public LoginMember(Long id, String email, Integer age) {
        this.id = id;
        this.email = email;
        this.age = age;
        this.type = LoginMemberType.getLoginMemberType(age);
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
    public LoginMemberType getType() {
        return type;
    }
}
