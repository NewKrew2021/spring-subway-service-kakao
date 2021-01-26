package subway.exception.custom;

public class NoSuchPathException extends RuntimeException {
    public NoSuchPathException() {
        super("경로가 존재하지 않습니다.");
    }
}
