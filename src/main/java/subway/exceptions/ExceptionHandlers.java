package subway.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Void> noElement() {
        return ResponseEntity.badRequest().build();
    }
}
