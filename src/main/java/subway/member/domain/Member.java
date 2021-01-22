package subway.member.domain;

import java.util.Objects;

public class Member {
    private Long id;
    private String email;
    private String password;
    private Integer age;

    public Member() {
    }

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member(Long id, String email, String password, Integer age) {
        this(email, password);
        this.id = id;
        this.age = age;
    }

    public Member(Long id, String email, Integer age) {
        this.id = id;
        this.email = email;
        this.age = age;
    }

    public Member(String email, String password, Integer age) {
        this(email, password);
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

    public void checkValidMember(Member member) {
        if(!this.equals(member)){
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(email, member.email) && Objects.equals(password, member.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
