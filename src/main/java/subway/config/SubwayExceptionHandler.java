package subway.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import subway.auth.exception.AuthException;

@RestControllerAdvice
public class SubwayExceptionHandler {

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public void handleAuthException(Exception e) {
        e.printStackTrace();
    }
}
