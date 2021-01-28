package subway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> invalidTokenHandle() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(InvalidMemberException.class)
    public ResponseEntity<String> invalidMemberHandle() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(StationNotFoundException.class)
    public ResponseEntity<String> StationNotFoundHandle() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> InvalidInputHandle() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
