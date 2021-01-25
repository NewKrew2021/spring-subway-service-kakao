package subway.exception;

public class LoginFailException extends RuntimeException {
    public LoginFailException() {
        super("인증에 실패하였습니다.");
    }
}
