package subway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import subway.exception.*;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(AlreadyExistedDataException.class)
    public ResponseEntity<String> alreadyExistedData(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(AuthorizationFailException.class)
    public ResponseEntity<String> authorizationFail() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증이 실패하였습니다.");
    }

    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity<String> loginFailException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 실패하였습니다.");
    }

    @ExceptionHandler(NotExistDataException.class)
    public ResponseEntity<String> notExistMemberException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(NoMoreSectionToDeleteException.class)
    public ResponseEntity<String> noMoreSectionToDeleteException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("더이상 역을 삭제할 수 없습니다.");
    }

    @ExceptionHandler(WrongInputDataException.class)
    public ResponseEntity<String> wrongInputDataException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
