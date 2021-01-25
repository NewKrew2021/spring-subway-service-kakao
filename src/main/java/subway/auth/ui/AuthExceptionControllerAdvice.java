package subway.auth.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import subway.auth.exception.InvalidTokenException;
import subway.favorite.exception.FavoriteNotFoundException;
import subway.line.exception.SectionNotFoundException;

import java.sql.SQLException;

@RestControllerAdvice
public class AuthExceptionControllerAdvice {

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Void> invalidToken() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity handleSQLException() {
        return ResponseEntity.badRequest().build();
    }
}
