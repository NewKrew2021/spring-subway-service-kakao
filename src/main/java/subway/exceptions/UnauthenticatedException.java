package subway.exceptions;

public class UnauthenticatedException extends RuntimeException{
    public UnauthenticatedException() {
        super("인증되지 않은 사용자입니다.");
    }
}
