package subway.auth.exception;

public class InvalidTokenException extends AuthException {
    private static final String INVALID_TOKEN_ERROR = "유효하지 않은 토큰입니다.";

    public InvalidTokenException() {
        super(INVALID_TOKEN_ERROR);
    }
}
