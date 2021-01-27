package subway.member.domain;

import subway.common.domain.Age;

public class Member {
    private final Long id;
    private final String email;
    private final String password;
    private final Age age;

    private Member(Long id, String email, String password, Age age) {
        validate(email, password, age);
        this.id = id;
        this.email = email;
        this.password = password;
        this.age = age;
    }

    private void validate(String email, String password, Age age) {
        if (isEmpty(email)) {
            throw new IllegalArgumentException("이메일이 비어있습니다.");
        }
        if (isEmpty(password)) {
            throw new IllegalArgumentException("비밀번호가 비어있습니다.");
        }
        if (isEmpty(age)) {
            throw new IllegalArgumentException("나이가 비어있습니다.");
        }
    }

    public static Member of(Long id, String email, String password, Age age) {
        return new Member(id, email, password, age);
    }

    public static Member of(String email, String password, Age age) {
        return new Member(null, email, password, age);
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private boolean isEmpty(Age age) {
        return age == null;
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

    public Age getAge() {
        return age;
    }
}
