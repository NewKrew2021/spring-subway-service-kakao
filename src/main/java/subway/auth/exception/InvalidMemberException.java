package subway.auth.exception;

public class InvalidMemberException extends IllegalArgumentException {
    public InvalidMemberException(String message) {
        super(message);
    }
}
