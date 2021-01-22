package subway.path.dto;

import subway.station.domain.Station;

import java.util.List;

public class PathResult {
    private List<Station> stations;
    private int distance;

    public PathResult() {
    }

    public PathResult(List<Station> stations, int distance) {
        this.stations = stations;
        this.distance = distance;
    }

    public List<Station> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }
}
