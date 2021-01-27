package subway.exceptions;

public class AuthException extends IllegalArgumentException{

    public static final String INVALID_USER_INFORMATION_ERROR_MESSAGE = "유효하지 않은 입력입니다.";
    public AuthException() {
        super(INVALID_USER_INFORMATION_ERROR_MESSAGE);
    }
}
