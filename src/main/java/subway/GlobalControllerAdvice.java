package subway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import subway.exceptions.AuthException;
import subway.exceptions.NotExistsDataException;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity authExceptionHandler(AuthException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(NotExistsDataException.class)
    public ResponseEntity notExistsExceptionHandler(NotExistsDataException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity handleSQLException() {
        return ResponseEntity.badRequest().build();
    }
}
