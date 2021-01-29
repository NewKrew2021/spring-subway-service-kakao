package subway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import subway.auth.exception.InvalidLoginException;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<String> catchInvalidLoginException(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
