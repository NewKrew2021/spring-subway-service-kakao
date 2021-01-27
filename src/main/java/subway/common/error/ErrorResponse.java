package subway.common.error;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private HttpStatus status;
    private String message;

    public ErrorResponse() {
    }

    private ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ErrorResponse from(Exception e) {
        return new ErrorResponse(ErrorStatus.from(e).getHttpStatus(), e.getMessage());
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
