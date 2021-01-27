package subway.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import subway.auth.exception.AuthException;
import subway.line.exception.InvalidStationIdException;
import subway.member.exceptions.InvalidAgeException;
import subway.path.exceptions.InvalidPathException;
import subway.path.exceptions.UnconnectedPathException;

@RestControllerAdvice
public class SubwayExceptionHandler {

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public void handleAuthException(Exception e) {
        e.printStackTrace();
    }

    @ExceptionHandler({
            InvalidStationIdException.class,
            InvalidAgeException.class,
            InvalidPathException.class,
            UnconnectedPathException.class,
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleBadRequestException(Exception e) {
        e.printStackTrace();
    }
}
