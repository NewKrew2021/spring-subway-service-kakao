package subway.exception.custom;

public class CannotAddSectionException extends RuntimeException {
    public CannotAddSectionException() {
        super("해당 입력으로 구간을 추가할 수 없습니다.");
    }
}