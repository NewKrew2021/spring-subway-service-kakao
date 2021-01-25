package subway.member.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import subway.member.exception.InvalidMemberException;

@RestControllerAdvice
public class MemberExceptionControllerAdvice {

    @ExceptionHandler(InvalidMemberException.class)
    public ResponseEntity<Void> invalidMember() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
