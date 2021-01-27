package subway.auth.exception;

public class LoginException extends AuthException {

    private static final String MESSAGE = "이메일 혹은 비밀번호가 일치하지 않습니다.";

    public LoginException() {
        super(MESSAGE);
    }
}
