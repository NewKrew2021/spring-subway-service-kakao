package subway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import subway.auth.exception.AuthException;

@RestControllerAdvice
public class SubwayAdvice {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Void> handleAuthException(Exception e) {
        e.printStackTrace();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
