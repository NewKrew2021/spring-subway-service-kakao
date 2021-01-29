package subway.exception;

public class InvalidStationException extends RuntimeException{
    public InvalidStationException(){
        super("잘못된 역으로의 접근입니다.");
    }
}
