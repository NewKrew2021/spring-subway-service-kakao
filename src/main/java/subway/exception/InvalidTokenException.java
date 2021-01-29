package subway.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String error) {
        super(error);
    }

}
