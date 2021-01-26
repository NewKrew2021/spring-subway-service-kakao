package subway.exception;

public class NoneAuthorizationException extends RuntimeException{

    private static final String NONE_AUTHORIZATION_EXCEPTION = "승인받지 않은 사용자입니다.";

    public NoneAuthorizationException() {
        super(NONE_AUTHORIZATION_EXCEPTION);
    }
}
