package subway.member.exceptions;

import subway.exceptions.AuthorizationException;

public class InvalidUserInfoException extends AuthorizationException {
    public InvalidUserInfoException(String message) {
        super(message);
    }
}
