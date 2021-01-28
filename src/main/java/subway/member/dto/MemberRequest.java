package subway.member.dto;

import subway.exception.InvalidInputException;
import subway.member.domain.Member;

public class MemberRequest {
    private String email;
    private String password;
    private Integer age;

    public MemberRequest() {
    }

    public MemberRequest(String email, String password, Integer age) {
        validInput(email, password, age);

        this.email = email;
        this.password = password;
        this.age = age;
    }

    private void validInput(String email, String password, Integer age) {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidInputException("이메일 입력 존재하지 않습니다.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new InvalidInputException("비밀번호 입력이 존재하지 않습니다.");
        }
        if (age == null || age < 0) {
            throw new InvalidInputException("나이가 입력되지 않았거나, 유효하지 않은 나이입니다.");
        }
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
