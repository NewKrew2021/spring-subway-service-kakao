package subway.auth.exception;

public class InvalidPasswordException extends AuthException {
    private static final String INVALID_PASSWORD_ERROR = "패스워드가 일치하지 않습니다.";

    public InvalidPasswordException() {
        super(INVALID_PASSWORD_ERROR);
    }
}
