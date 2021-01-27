package subway.exception.custom;

public class InvalidLineException extends RuntimeException {
    public InvalidLineException() {
        super("유효하지 않는 노선입니다.");
    }
}