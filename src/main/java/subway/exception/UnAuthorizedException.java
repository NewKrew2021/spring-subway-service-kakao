package subway.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends RuntimeException {
    private HttpStatus status = HttpStatus.UNAUTHORIZED;

    public UnAuthorizedException() {
        super("UnAuthorized request");
    }

    public UnAuthorizedException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
