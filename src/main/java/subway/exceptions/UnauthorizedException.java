package subway.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("인가되지 않은 사용자입니다.");
    }
}
