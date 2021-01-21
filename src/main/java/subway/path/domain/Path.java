package subway.path.domain;

import subway.station.domain.Station;

import java.util.List;

public class Path {
    private List<Station> stations;
    private int distance;
    private int fare;

    public Path(List<Station> stations, int distance) {
        this.stations = stations;
        this.distance = distance;
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
