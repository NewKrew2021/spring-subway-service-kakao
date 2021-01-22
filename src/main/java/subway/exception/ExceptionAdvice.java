package subway.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity<String> handleDataAccessException(Exception e) {
        return ResponseEntity.status(401).body("잘못된 입력입니다.");
    }
}
