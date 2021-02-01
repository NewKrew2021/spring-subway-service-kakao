package subway.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenRequestNullException extends RuntimeException {
    public TokenRequestNullException(String message) {
        super(message);
    }
}
