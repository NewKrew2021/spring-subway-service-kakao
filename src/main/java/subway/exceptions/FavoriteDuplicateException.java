package subway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FavoriteDuplicateException extends RuntimeException {
    public FavoriteDuplicateException() {
    }

    public FavoriteDuplicateException(String message) {
        super(message);
    }
}
