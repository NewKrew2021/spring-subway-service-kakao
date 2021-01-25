package subway.auth.exceptions;

import subway.exceptions.AuthException;

public class UnauthorizedUserException extends AuthException {
    public UnauthorizedUserException(String message) {
        super(message);
    }
}
