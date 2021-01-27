package subway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FavoriteSameArgumentException extends RuntimeException {
    public FavoriteSameArgumentException(String message) {
        super(message);
    }
}
