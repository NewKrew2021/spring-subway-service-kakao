package subway.line.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import subway.line.exception.SectionNotFoundException;

@RestControllerAdvice
public class LineExceptionControllerAdvice {

    @ExceptionHandler(SectionNotFoundException.class)
    public ResponseEntity<Void> NullSection() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
