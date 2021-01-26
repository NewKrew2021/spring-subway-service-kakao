package subway.exception;

public class InvalidTokenException extends RuntimeException {


    public static final String MESSAGE = "유효한 토큰이 아닙니다.";

    public InvalidTokenException() {
        super(MESSAGE);
    }

    public InvalidTokenException(String error) {
        super(error);
    }

}
