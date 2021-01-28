package subway.favorite.exception;

public class InvalidFavoriteException extends IllegalArgumentException {
    public InvalidFavoriteException(String message) {
        super(message);
    }
}
