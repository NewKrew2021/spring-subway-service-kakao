package subway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotExistException extends RuntimeException{
    public NotExistException() {
    }

    public NotExistException(String message) {
        super(message);
    }
}
