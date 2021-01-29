package subway.common.exception;

public class InvalidTokenException extends RuntimeException {

    public static final String INVALID_TOKEN = "유효하지 않는 토큰입니다.";

    private final String message;

    public InvalidTokenException(String message) {
        this.message = message;
    }

}
