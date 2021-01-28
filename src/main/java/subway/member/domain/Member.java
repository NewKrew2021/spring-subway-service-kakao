package subway.member.domain;

public class Member {
    private final Long id;
    private final String email;
    private final String password;
    private final Integer age;

    public Member(String email, String password, Integer age) {
        this(0L, email, password, age);
    }

    public Member(Long id, String email, String password, Integer age) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getAge() {
        return age;
    }
}
