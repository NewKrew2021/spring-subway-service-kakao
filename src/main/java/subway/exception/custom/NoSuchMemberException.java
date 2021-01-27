package subway.exception.custom;

public class NoSuchMemberException extends RuntimeException {
    public NoSuchMemberException() {
        super("존재하지 않는 멤버입니다.");
    }
}
