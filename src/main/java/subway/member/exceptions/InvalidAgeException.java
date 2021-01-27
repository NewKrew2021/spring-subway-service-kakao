package subway.member.exceptions;

public class InvalidAgeException extends RuntimeException {
    private static final String MESSAGE = "비정상적인 나이입니다.";

    public InvalidAgeException() {
        super(MESSAGE);
    }
}
