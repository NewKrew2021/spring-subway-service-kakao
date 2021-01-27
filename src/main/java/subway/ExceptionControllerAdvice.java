package subway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import subway.auth.exception.InvalidTokenException;
import subway.favorite.exception.FavoriteNotFoundException;
import subway.line.exception.SectionNotFoundException;
import subway.member.exception.InvalidMemberException;

import java.sql.SQLException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Void> invalidToken() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity handleSQLException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(FavoriteNotFoundException.class)
    public ResponseEntity<Void> NullFavorite() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(SectionNotFoundException.class)
    public ResponseEntity<Void> NullSection() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(InvalidMemberException.class)
    public ResponseEntity<Void> invalidMember() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Void> EmptyMember() {
        return ResponseEntity.badRequest().build();
    }
}
