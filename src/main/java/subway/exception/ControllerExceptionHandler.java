package subway.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;


@ControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(InvalidTokenException.class)
    protected ResponseEntity<Void> handleInvalidTokenException(InvalidTokenException e){
        logger.error("handleInvalidTokenException", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(LoginFailException.class)
    protected ResponseEntity<Void> handleLoginFailException(LoginFailException e){
        logger.error("handleLoginFailException", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<Void> handleSQLException(SQLException e){
        logger.error("handleSQLException");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(RequestPermissionDeniedException.class)
    protected ResponseEntity<Void> handleRequestPermissionDeniedException(RequestPermissionDeniedException e){
        logger.error("handleRequestPermissionDeniedException");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
