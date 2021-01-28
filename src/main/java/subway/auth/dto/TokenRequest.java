package subway.auth.dto;

import subway.exception.InvalidInputException;

public class TokenRequest {
    private String email;
    private String password;

    public TokenRequest() {
    }

    public TokenRequest(String email, String password) {
        validInput(email, password);
        this.email = email;
        this.password = password;
    }

    private void validInput(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidInputException("이메일 입력 존재하지 않습니다.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new InvalidInputException("비밀번호 입력이 존재하지 않습니다.");
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
