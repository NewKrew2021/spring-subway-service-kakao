package subway.common.error;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import subway.common.exception.AlreadyExistException;
import subway.common.exception.AuthorizationException;
import subway.common.exception.NegativeNumberException;
import subway.common.exception.NotExistException;

import java.sql.SQLException;
import java.util.Arrays;

public enum ErrorStatus {
    AUTHORIZATION(HttpStatus.UNAUTHORIZED, AuthorizationException.class),
    NEGATIVE_NUMBER(HttpStatus.BAD_REQUEST, NegativeNumberException.class),
    NOT_EXIST(HttpStatus.BAD_REQUEST, NotExistException.class),
    ALREADY_EXIST(HttpStatus.BAD_REQUEST, AlreadyExistException.class),
    DATA_ACCESS(HttpStatus.BAD_REQUEST, DataAccessException.class),
    SQL(HttpStatus.BAD_REQUEST, SQLException.class),
    ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, IllegalArgumentException.class),
    ILLEGAL_STATE(HttpStatus.INTERNAL_SERVER_ERROR, IllegalStateException.class),
    DEFAULT(HttpStatus.INTERNAL_SERVER_ERROR, Exception.class)
    ;

    private final HttpStatus httpStatus;
    private final Class<? extends Exception> exception;

    ErrorStatus(HttpStatus httpStatus, Class<? extends Exception> exception) {
        this.httpStatus = httpStatus;
        this.exception = exception;
    }

    public static ErrorStatus from(Exception exception) {
        return Arrays.stream(ErrorStatus.values())
                .filter(errorStatus -> errorStatus.exception.isInstance(exception))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 에러 타입이 없습니다."));
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
