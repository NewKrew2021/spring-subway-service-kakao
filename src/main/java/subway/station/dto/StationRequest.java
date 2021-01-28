package subway.station.dto;


import subway.exception.InvalidInputException;
import subway.station.domain.Station;

public class StationRequest {

    private String name;

    public StationRequest() {
    }

    public StationRequest(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("역의 이름이 입력되지 않았습니다.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Station toStation() {
        return new Station(name);
    }

}
