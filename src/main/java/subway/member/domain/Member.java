package subway.member.domain;

public class Member {
    public static final long DEFAULT_ID = 0L;
    private Long id;
    private String email;
    private String password;
    private Integer age;

    public Member() {
    }

    private Member(Long id, String email, String password, Integer age) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.age = age;
    }

    private Member(String email, String password, Integer age) {
        this(DEFAULT_ID, email, password, age);
    }

    public static Member of(Long id, String email, String password, Integer age) {
        return new Member(id, email, password, age);
    }

    public static Member of(String email, String password, Integer age) {
        return new Member(email, password, age);
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
