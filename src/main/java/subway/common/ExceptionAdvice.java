package subway.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import subway.common.exception.InvalidLoginException;
import subway.common.exception.InvalidTokenException;

import java.sql.SQLException;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler({InvalidLoginException.class, InvalidTokenException.class})
    public ResponseEntity<String> catchInvalidLoginException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler({SQLException.class, IllegalStateException.class})
    public ResponseEntity handleBadRequest() {
        return ResponseEntity.badRequest().build();
    }

}
