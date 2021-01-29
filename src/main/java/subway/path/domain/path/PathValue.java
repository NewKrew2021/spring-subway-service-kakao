package subway.path.domain.path;

import subway.station.domain.Station;

import java.time.LocalDateTime;
import java.util.List;

public class PathValue {
    private final List<Station> stations;
    private final int distance;
    private final int fare;
    private final LocalDateTime arrivalAt;

    public PathValue(List<Station> stations, int distance, int fare, LocalDateTime arrivalAt) {
        this.stations = stations;
        this.distance = distance;
        this.fare = fare;
        this.arrivalAt = arrivalAt;
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

    public LocalDateTime getArrivalAt() {
        return arrivalAt;
    }
}
