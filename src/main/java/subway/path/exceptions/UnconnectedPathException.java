package subway.path.exceptions;

public class UnconnectedPathException extends RuntimeException {

    private static final String MESSAGE = "경로를 찾을 수 없습니다.";

    public UnconnectedPathException() {
        super(MESSAGE);
    }
}
