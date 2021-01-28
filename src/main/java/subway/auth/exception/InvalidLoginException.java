package subway.auth.exception;

public class InvalidLoginException extends RuntimeException{

    public static final String EMAIL_PASSWORD_MISMATCH = "이메일과 패스워드가 일치하지 않습니다.";
    public static final String EMAIL_NOT_EXIST = "이메일이 존재하지 않습니다.";

    private final String message;

    public InvalidLoginException(String message) {
        this.message = message;
    }
}
