package subway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import subway.exception.*;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(AlreadyExistedEmailException.class)
    public ResponseEntity alreadyExistedEmail() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(AlreadyExistedSectionException.class)
    public ResponseEntity alreadyExistedSection() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(AuthorizationFailException.class)
    public ResponseEntity authorizationFail() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(DuplicateNameException.class)
    public ResponseEntity duplicateNameException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity loginFailException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(NoMoreSectionToDeleteException.class)
    public ResponseEntity noMoreSectionToDeleteException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(NotExistLineException.class)
    public ResponseEntity notExistLineException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(NotExistMemberException.class)
    public ResponseEntity notExistMemberException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(NotExistStationException.class)
    public ResponseEntity notExistStationException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(TooLowAgeException.class)
    public ResponseEntity tooLowAgeException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(TooLowDistanceException.class)
    public ResponseEntity tooLowDistanceException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(TooLowExtraFareException.class)
    public ResponseEntity tooLowExtraFareException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(WrongStationIdException.class)
    public ResponseEntity wrongStationIdException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(WrongEmailFormatException.class)
    public ResponseEntity wrongEmailFormatException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
