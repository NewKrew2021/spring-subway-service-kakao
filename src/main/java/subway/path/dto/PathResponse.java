package subway.path.dto;

import subway.path.domain.path.PathValue;
import subway.station.dto.StationResponse;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class PathResponse {
    private final List<StationResponse> stations;
    private final int distance;
    private final int fare;
    private final String arrivalAt;

    private PathResponse(List<StationResponse> stations, int distance, int fare, String arrivalAt) {
        this.stations = stations;
        this.distance = distance;
        this.fare = fare;
        this.arrivalAt = arrivalAt;
    }

    public static PathResponse from(PathValue path) {
        return new PathResponse(
                StationResponse.listOf(path.getStations()),
                path.getDistance(),
                path.getFare(),
                path.getArrivalAt().format(DateTimeFormatter.ofPattern("uuuuMMddhhmm"))
        );
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

    public String getArrivalAt() {
        return arrivalAt;
    }
}
