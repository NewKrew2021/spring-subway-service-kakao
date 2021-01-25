package subway.auth.exceptions;

import subway.exceptions.AuthException;

public class InvalidTokenException extends AuthException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
