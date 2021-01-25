package subway.exceptions;

public class AuthorizationException extends IllegalArgumentException{
    public AuthorizationException(String message) {
        super(message);
    }
}
