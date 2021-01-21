package subway.line.exception;

public class InvalidStationIdException extends RuntimeException {
    private static final String INVALID_STATION_ID_ERROR = "존재하지 않는 Station Id 입니다. ID : ";

    public InvalidStationIdException(Long id) {
        super(INVALID_STATION_ID_ERROR + id);
    }
}
