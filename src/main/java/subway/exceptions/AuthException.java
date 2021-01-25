package subway.exceptions;

public class AuthException extends IllegalArgumentException{
    public AuthException(String message) {
        super(message);
    }
}
