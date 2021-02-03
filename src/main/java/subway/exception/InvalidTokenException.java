package subway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason="No such token")
public class InvalidTokenException extends RuntimeException{
    private static final String INVALID_TOKEN_MESSAGE = "유효하지 않은 토큰입니다.";

    public InvalidTokenException(){
        super(INVALID_TOKEN_MESSAGE);
    }

}
