package subway.favorite.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FavoriteNullRequestException extends NoSuchElementException {
    public FavoriteNullRequestException(String message) {
        super(message);
    }
}
