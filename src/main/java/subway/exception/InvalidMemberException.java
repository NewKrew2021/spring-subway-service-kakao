package subway.exception;

public class InvalidMemberException extends RuntimeException{

    private static final String MESSAGE = "이메일이 없거나, 패스워드가 일치하지 않음";

    public InvalidMemberException() {
        super(MESSAGE);
    }

    public InvalidMemberException(String error){
        super(error);
    }

}
