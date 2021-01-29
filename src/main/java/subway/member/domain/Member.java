package subway.member.domain;

import subway.exception.LoginFailException;
import subway.exception.WrongInputDataException;

import java.util.Objects;

public class Member {
    public static final int MIN_AGE = 1;
    public static final String EMAIL_RULE_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private Long id;
    private String email;
    private String password;
    private Integer age;

    public Member() {
    }

    public Member(String email, String password, Integer age) {
        String emailRule = EMAIL_RULE_REGEX;
        if (!email.matches(emailRule)) {
            throw new WrongInputDataException("이메일 형식이 잘못되었습니다.");
        }
        if (age < MIN_AGE) {
            throw new WrongInputDataException("연령이 잘못 입력되었습니다.");
        }
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public Member(Long id, String email, String password, Integer age) {
        this(email, password, age);
        this.id = id;
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

    public boolean equalPassword(String password) {
        return this.password.equals(password);
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
