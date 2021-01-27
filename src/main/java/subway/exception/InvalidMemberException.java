package subway.exception;

public class InvalidMemberException extends RuntimeException{
    public InvalidMemberException(){
        super("이메일이 없거나, 패스워드가 일치하지 않음");
    }
}
