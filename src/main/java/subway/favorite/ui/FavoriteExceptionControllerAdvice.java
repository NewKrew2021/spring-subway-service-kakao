package subway.favorite.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import subway.favorite.exception.FavoriteNotFoundException;

@RestControllerAdvice
public class FavoriteExceptionControllerAdvice {

    @ExceptionHandler(FavoriteNotFoundException.class)
    public ResponseEntity<Void> NullFavorite() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
