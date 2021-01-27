package subway.path.domain;

import subway.station.domain.Station;

import java.util.List;

public class PathInfo {
    private final List<Station> stations;
    private final int distance;
    private final int fare;

    public PathInfo(List<Station> stations, int distance, int fare) {
        this.stations = stations;
        this.distance = distance;
        this.fare = fare;
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
