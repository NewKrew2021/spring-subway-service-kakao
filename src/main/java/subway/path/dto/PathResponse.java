package subway.path.dto;

import subway.path.domain.Path;
import subway.station.dto.StationResponse;

import java.util.List;

public class PathResponse {
    private List<StationResponse> stations;
    private int distance;
    private int fare;

    public PathResponse() {
    }

    public PathResponse(Path path) {
        this.stations = StationResponse.listOf(path.getStations());
        this.distance = path.getDistance();
        this.fare = path.getFare();
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }

    public int getFare() {
        return fare;
    }
}
