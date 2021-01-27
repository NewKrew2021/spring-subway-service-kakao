package subway.member.domain;

public class Member extends LoginMember {
    private String password;

    public Member() {
    }

    public Member(Long id, String email, String password, Integer age) {
        super(id, email, age);
        this.password = password;
    }

    public Member(String email, String password, Integer age) {
        super(email, age);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
