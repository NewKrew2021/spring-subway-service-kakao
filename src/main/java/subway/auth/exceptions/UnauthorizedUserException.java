package subway.auth.exceptions;

import subway.exceptions.AuthorizationException;


public class UnauthorizedUserException extends AuthorizationException {
    public UnauthorizedUserException(String message) {
        super(message);
    }
}
