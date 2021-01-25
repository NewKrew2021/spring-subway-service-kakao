package subway.auth.exceptions;

import subway.exceptions.AuthorizationException;

public class InvalidTokenException extends AuthorizationException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
