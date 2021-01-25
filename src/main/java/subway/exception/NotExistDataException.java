package subway.exception;

public class NotExistDataException extends RuntimeException {
    public NotExistDataException(String message) {
        super(message);
    }

    public NotExistDataException() {
    }
}
