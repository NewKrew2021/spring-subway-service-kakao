package subway.exception;

public class InvalidTokenException extends RuntimeException{
    private static final String INVALID_TOKEN_MESSAGE = "유효 하지 않는 토큰 입니다.";

    public InvalidTokenException(){
        super(INVALID_TOKEN_MESSAGE);
    }

}
