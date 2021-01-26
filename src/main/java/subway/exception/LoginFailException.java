package subway.exception;

public class LoginFailException extends RuntimeException {
    public LoginFailException(String message){
        super(message);
    }
}
