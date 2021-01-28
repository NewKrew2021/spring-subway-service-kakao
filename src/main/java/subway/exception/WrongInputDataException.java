package subway.exception;

public class WrongInputDataException extends IllegalArgumentException {
    public WrongInputDataException() {
    }

    public WrongInputDataException(String s) {
        super(s);
    }
}
