package subway.line.exception;

public class LineNotFoundException extends RuntimeException{
    public LineNotFoundException() {
    }

    @Override
    public String getMessage() {
        return "해당 Line을 찾지 못했습니다.";
    }
}
