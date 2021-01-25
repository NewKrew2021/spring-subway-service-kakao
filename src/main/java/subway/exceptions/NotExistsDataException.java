package subway.exceptions;

public class NotExistsDataException extends IllegalArgumentException{
    public NotExistsDataException(String message) {
        super(message);
    }
}
