package subway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import subway.exception.*;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(AlreadyExistedDataException.class)
    public ResponseEntity<String> alreadyExistedData() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 데이터 입니다.");
    }

    @ExceptionHandler(AuthorizationFailException.class)
    public ResponseEntity<String> authorizationFail() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증이 실패하였습니다.");
    }

    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity<String> loginFailException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 실패하였습니다.");
    }

    @ExceptionHandler(DuplicateNameException.class)
    public ResponseEntity<String> duplicateNameException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 이름입니다.");
    }

    @ExceptionHandler(NoMoreSectionToDeleteException.class)
    public ResponseEntity<String> noMoreSectionToDeleteException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("더이상 역을 삭제할 수 없습니다.");
    }

    @ExceptionHandler(NotExistLineException.class)
    public ResponseEntity<String> notExistLineException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 노선입니다.");
    }

    @ExceptionHandler(NotExistMemberException.class)
    public ResponseEntity<String> notExistMemberException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 사용자 입니다.");
    }

    @ExceptionHandler(NotExistStationException.class)
    public ResponseEntity<String> notExistStationException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 역입니다.");
    }

    @ExceptionHandler(TooLowAgeException.class)
    public ResponseEntity<String> tooLowAgeException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("나이가 잘못되었습니다.");
    }

    @ExceptionHandler(TooLowDistanceException.class)
    public ResponseEntity<String> tooLowDistanceException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("거리가 잘못되었습니다.");
    }

    @ExceptionHandler(TooLowExtraFareException.class)
    public ResponseEntity<String> tooLowExtraFareException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("추가요금이 잘못되었습니다.");
    }

    @ExceptionHandler(WrongStationIdException.class)
    public ResponseEntity<String> wrongStationIdException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 역 아이디 입니다.");
    }

    @ExceptionHandler(WrongEmailFormatException.class)
    public ResponseEntity<String> wrongEmailFormatException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 이메일 형식입니다.");
    }
}
