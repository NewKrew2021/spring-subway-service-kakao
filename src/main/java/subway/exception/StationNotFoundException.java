package subway.exception;

public class StationNotFoundException extends RuntimeException{

    public static final String MESSAGE = "해당 아이디의 역을 찾을 수 없습니다.";

    public StationNotFoundException() {
        super(MESSAGE);
    }

    public StationNotFoundException(String error) {
        super(error);
    }

}
