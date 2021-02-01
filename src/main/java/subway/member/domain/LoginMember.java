package subway.member.domain;

import subway.common.domain.Age;

public class LoginMember {
    private final Long id;
    private final String email;
    private final Age age;

    private LoginMember(Long id, String email, Age age) {
        validate(email, age);
        this.id = id;
        this.email = email;
        this.age = age;
    }

    private void validate(String email, Age age) {
        if (isEmpty(email)) {
            throw new IllegalArgumentException("이메일이 비어있습니다.");
        }
        if (isEmpty(age)) {
            throw new IllegalArgumentException("나이가 비어있습니다.");
        }
    }

    public static LoginMember from(Member member) {
        return new LoginMember(member.getId(), member.getEmail(), member.getAge());
    }

    public static LoginMember of(Long id, String email, Age age) {
        return new LoginMember(id, email, age);
    }

    private boolean isEmpty(String email) {
        return email == null || email.isEmpty();
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

    public Age getAge() {
        return age;
    }
}
