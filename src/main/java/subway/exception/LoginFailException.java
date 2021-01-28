package subway.exception;

public class LoginFailException extends RuntimeException{
    private static final String LOGIN_FAIL_MESSAGE = "로그인에 실패했습니다.";

    public LoginFailException(){
        super(LOGIN_FAIL_MESSAGE);
    }
}
