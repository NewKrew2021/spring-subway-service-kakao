package subway.path.exceptions;

public class InvalidPathException extends RuntimeException {
    private static final String MESSAGE = "비정상적인 path입니다.";

    public InvalidPathException() {
        super(MESSAGE);
    }
}
