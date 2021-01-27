package subway.station.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class StationRequest {
    private final String name;

    @JsonCreator
    public StationRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Station toStation() {
        return new Station(name);
    }
}
