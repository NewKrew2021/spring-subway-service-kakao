package subway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import subway.auth.exception.AuthException;
import subway.path.exceptions.UnconnectedPathException;

@RestControllerAdvice
public class SubwayAdvice {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<String> handleAuthException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(UnconnectedPathException.class)
    public ResponseEntity<String> exceptionHandle(Exception e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
