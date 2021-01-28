package subway.path.dto;

import subway.path.domain.PathValue;
import subway.station.domain.Station;

import java.util.List;

public class PathResponse {
    private List<Station> stations;
    private int distance;
    private int fare;

    public PathResponse() {
    }

    public PathResponse(PathValue path) {
        this.stations = path.getStations();
        this.distance = path.getDistance();
        this.fare = path.getFare();
    }

    public List<Station> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }

    public int getFare() {
        return fare;
    }
}
