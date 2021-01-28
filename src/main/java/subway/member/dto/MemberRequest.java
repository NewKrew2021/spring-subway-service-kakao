package subway.member.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import subway.member.domain.Member;

public class MemberRequest {
    private final String email;
    private final String password;
    private final Integer age;

    @JsonCreator
    public MemberRequest(String email, String password, Integer age) {
        this.email = email;
        this.password = password;
        this.age = age;
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

    public Member toMember() {
        return new Member(email, password, age);
    }
}
