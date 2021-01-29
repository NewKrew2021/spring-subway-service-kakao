package subway.line.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SectionException extends RuntimeException {

    public SectionException(String message) {
        super(message);
    }
}
