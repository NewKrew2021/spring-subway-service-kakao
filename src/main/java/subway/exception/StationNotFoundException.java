package subway.exception;

public class StationNotFoundException extends RuntimeException{

    public StationNotFoundException(String error) {
        super(error);
    }

}
