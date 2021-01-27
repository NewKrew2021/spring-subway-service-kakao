package subway.exception.custom;

public class CannotDeleteSectionException extends RuntimeException {
    public CannotDeleteSectionException() {
        super("구간을 삭제할 수 없습니다.");
    }
}