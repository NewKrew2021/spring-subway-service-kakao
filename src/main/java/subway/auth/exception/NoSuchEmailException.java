package subway.auth.exception;

public class NoSuchEmailException extends AuthException {
    private static final String NO_SUCH_EMAIL_ERROR = "해당하는 이메일이 존재하지 않습니다.";

    public NoSuchEmailException() {
        super(NO_SUCH_EMAIL_ERROR);
    }
}
