package subway.exception;

public class AlreadyExistedDataException extends IllegalArgumentException {
    public AlreadyExistedDataException(String s) {
        super(s);
    }

    public AlreadyExistedDataException() {
    }
}
